package com.orcchg.musicsquare.ui.music;

import android.support.annotation.NonNull;

import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.MvpView;

public interface MusicDetailsMvpView extends MvpView {

    void showMusicDetails(@NonNull Musician musician);
    void showError();
}
