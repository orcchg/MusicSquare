package com.orcchg.musicsquare;

import android.app.Application;

import com.orcchg.musicsquare.data.DataManager;

import timber.log.Timber;

public class MusicSquareApplication extends Application {

    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: exclude from release builds
        Timber.plant(new Timber.DebugTree());
        mDataManager = new DataManager(getApplicationContext());
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}
