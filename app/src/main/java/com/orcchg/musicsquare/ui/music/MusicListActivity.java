package com.orcchg.musicsquare.ui.music;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MusicListActivity extends BaseActivity<MusicListPresenter> implements MusicListMvpView {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.rv_musician_list) RecyclerView mMusiciansList;

    private MusiciansAdapter mMusiciansAdapter;

    @Override
    protected MusicListPresenter createPresenter() {
        return new MusicListPresenter();
    }

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadMusicians();
    }

    /* View implementation */
    // --------------------------------------------------------------------------------------------
    @Override
    public void showMusicList(List<Musician> musician) {
        mMusiciansAdapter.setMusicians(musician);  // TODO: set empty view
    }

    @Override
    public void showError() {

    }

    /* Internals */
    // --------------------------------------------------------------------------------------------
    private void initView() {
        setContentView(R.layout.activity_music_list);
        ButterKnife.bind(this);

        mMusiciansAdapter = new MusiciansAdapter();
        mMusiciansList.setLayoutManager(new LinearLayoutManager(this));
        mMusiciansList.setAdapter(mMusiciansAdapter);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.str_musicians_list);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}
