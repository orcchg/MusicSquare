package com.orcchg.musicsquare.data;

import android.content.Context;

import com.orcchg.musicsquare.data.local.MusiciansDatabase;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.data.remote.RestAdapter;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

    public MusiciansDatabase getDatabase() {
        return mDatabase;
    }

    /**
     * Force {@link DataManager} to fetch data from remote next time.
     * @param flag true to force fetching from remote.
     */
    public void invalidateCache(boolean flag) {
        mInvalidateCache = flag;
    }

    /**
     * Get a list of {@link Musician} items. Data could be fetched either from remote
     * or from a local storage {@link MusiciansDatabase}. This is done transparently for user,
     */
    public Observable<List<Musician>> getMusicians() {
        if (mInvalidateCache || mDatabase.isEmpty()) {
            Timber.d("Cache is empty - make HTTP request");
            return mRestAdapter.getMusicians("artists.json")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            Timber.d("Cache contains actual data");
            return mDatabase.getAllMusicians().flatMap(new Func1<List<Musician>, Observable<List<Musician>>>() {
                @Override
                public Observable<List<Musician>> call(List<Musician> musicians) {
                    return Observable.just(musicians);
                }
            }).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
