package com.orcchg.musicsquare;

import android.app.Application;

import com.orcchg.musicsquare.data.DataManager;
import com.orcchg.musicsquare.data.local.MusiciansDatabase;
import com.orcchg.musicsquare.data.remote.RestAdapter;

import timber.log.Timber;

public class MusicSquareApplication extends Application {

    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: exclude from release builds
        Timber.plant(new Timber.DebugTree());

        RestAdapter restAdapter = RestAdapter.Creator.create();
        MusiciansDatabase database = new MusiciansDatabase(getApplicationContext());
        mDataManager = new DataManager(restAdapter, database);
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}
