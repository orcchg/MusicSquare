package com.orcchg.musicsquare.ui.music;

import android.app.Activity;

import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BasePresenter;

public class MusicDetailsPresenter extends BasePresenter<MusicDetailsMvpView> {
    static final String EXTRA_MUSICIAN = "extra_musician";

    private Musician mMusician;

    @Override
    public void attachView(MusicDetailsMvpView mvpView) {
        super.attachView(mvpView);
        mMusician = ((Activity) mvpView).getIntent().getParcelableExtra(EXTRA_MUSICIAN);
    }

    void onStart() {
        checkViewAttached();
        getMvpView().showMusicDetails(mMusician);
    }
}
