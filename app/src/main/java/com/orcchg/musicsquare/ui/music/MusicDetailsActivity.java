package com.orcchg.musicsquare.ui.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MusicDetailsActivity extends BaseActivity<MusicDetailsPresenter> implements MusicDetailsMvpView {

    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tv_desription) TextView mDescriptionTextView;
    @Bind(R.id.tv_link) TextView mLinkTextView;

    @Override
    protected MusicDetailsPresenter createPresenter() {
        return new MusicDetailsPresenter();
    }

    public static Intent getIntent(Context context, @NonNull Musician musician) {
        Intent intent = new Intent(context, MusicDetailsActivity.class);
        intent.putExtra(MusicDetailsPresenter.EXTRA_MUSICIAN, musician);
        return intent;
    }

    /* Lifecycle */
    // --------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    /* View implementation */
    // --------------------------------------------------------------------------------------------
    @Override
    public void showMusicDetails(@NonNull Musician musician) {
        mCollapsingToolbar.setTitle(musician.getName());
        mToolbar.setTitle(musician.getName());
        mDescriptionTextView.setText(musician.getDescription());
        mLinkTextView.setText(musician.getLink());
    }

    /* Internals */
    // --------------------------------------------------------------------------------------------
    private void initView() {
        setContentView(R.layout.activity_music_details);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        @ColorInt int color = getResources().getColor(android.R.color.white);
        mCollapsingToolbar.setExpandedTitleColor(color);
        mCollapsingToolbar.setCollapsedTitleTextColor(color);
        mToolbar.setTitleTextColor(color);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
