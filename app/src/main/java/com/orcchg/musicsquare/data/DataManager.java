package com.orcchg.musicsquare.data;

import android.content.Context;

import com.orcchg.musicsquare.data.local.ByIdMusiciansSpecification;
import com.orcchg.musicsquare.data.local.MusiciansDatabase;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.data.remote.RestAdapter;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Core manager which provides an access to data. It handles internally whether to fetch data from
 * remote or just get it from a local storage.
 */
public class DataManager {

    private RestAdapter mRestAdapter;
    private MusiciansDatabase mDatabase;

    private boolean mInvalidateCache;

    public DataManager(Context context) {
        mRestAdapter = RestAdapter.Creator.create();
        mDatabase = new MusiciansDatabase(context);
    }

    /**
     * Force {@link DataManager} to fetch data from remote next time.
     * @param flag true to force fetching from remote.
     */
    public void invalidateCache(boolean flag) {
        mInvalidateCache = flag;
    }

    /**
     * Get a list of all {@link Musician} items. Data could be fetched either from remote
     * or from a local storage {@link MusiciansDatabase}. This is done transparently for user,
     */
    public Observable<List<Musician>> getMusicians() {
        if (mInvalidateCache || mDatabase.isEmpty()) {
            Timber.d("Cache is empty - make HTTP request");
            return mRestAdapter.getMusicians("artists.json")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnEach(new Observer<List<Musician>>() {
                        @Override
                        public void onCompleted() {
                            invalidateCache(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("Data error: %s", e.toString());
                        }

                        @Override
                        public void onNext(List<Musician> musicians) {
                            mDatabase.addMusicians(musicians);
                            onCompleted();
                        }
                    });
        } else {
            Timber.d("Cache contains actual data");
            return mDatabase.getAllMusicians()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * Get one {@link Musician} item by it's id. Returns a list with just a single item.
     */
    public Observable<List<Musician>> getMusician(long id) {
        return mDatabase.queryMusicians(new ByIdMusiciansSpecification(id))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
