package com.orcchg.musicsquare.data;

import android.content.Context;

import com.orcchg.musicsquare.data.local.Database;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.data.remote.RestAdapter;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Core class to manage data retrieving either from database or remote.
 */
public class DataManager {

    private RestAdapter mRestAdapter;
    private Database mDatabase;

    public DataManager(Context context) {
        mRestAdapter = RestAdapter.Creator.create();
        // TODO: initialize database
    }

    public Observable<List<Musician>> getMusicians() {
        return mRestAdapter.getMusicians("artists.json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
