package com.orcchg.musicsquare.ui.music;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BaseActivity;
import com.orcchg.musicsquare.util.GridItemDecorator;
import com.orcchg.musicsquare.util.ViewUtility;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MusicListActivity extends BaseActivity<MusicListPresenter> implements MusicListMvpView {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.rl_toolbar_dropshadow) View mDropshadowView;
    @Bind(R.id.rv_musician_list) RecyclerView mMusiciansList;
    @Bind(R.id.loading_view) View mLoadingView;
    @Bind(R.id.error_view) View mErrorView;
    @Bind(R.id.btn_retry) Button mErrorButton;

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
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mDropshadowView.setVisibility(View.VISIBLE);
        mMusiciansList.setVisibility(View.VISIBLE);
        mMusiciansAdapter.setMusicians(musician);  // TODO: set empty view
    }

    @Override
    public void showLoading() {
        mMusiciansList.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mDropshadowView.setVisibility(View.INVISIBLE);  // don't overlap with progress bar
    }

    @Override
    public void showError() {
        mMusiciansList.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mDropshadowView.setVisibility(View.VISIBLE);
    }

    /* Internals */
    // --------------------------------------------------------------------------------------------
    private void initView() {
        setContentView(R.layout.activity_music_list);
        ButterKnife.bind(this);

        if (ViewUtility.isLargeScreen(this)) {
            mMusiciansList.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_span)));
            mMusiciansList.addItemDecoration(new GridItemDecorator(this, (R.dimen.grid_card_spacing)));
        } else {
            mMusiciansList.setLayoutManager(new LinearLayoutManager(this));
        }

        mMusiciansAdapter = new MusiciansAdapter();
        mMusiciansList.setAdapter(mMusiciansAdapter);

        mErrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onRetry();
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.str_musicians_list);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}
