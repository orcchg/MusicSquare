package com.orcchg.musicsquare;

import android.app.Application;

import timber.log.Timber;

public class MusicSquareApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: exclude from release builds
        Timber.plant(new Timber.DebugTree());
    }
}
