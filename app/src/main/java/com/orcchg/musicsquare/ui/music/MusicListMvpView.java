package com.orcchg.musicsquare.ui.music;

import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.MvpView;

import java.util.List;

public interface MusicListMvpView extends MvpView {

    void showMusicList(List<Musician> musician);
    void showError();
}
