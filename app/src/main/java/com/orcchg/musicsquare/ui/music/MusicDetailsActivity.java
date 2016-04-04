package com.orcchg.musicsquare.ui.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class MusicDetailsActivity extends BaseActivity<MusicDetailsPresenter> implements MusicDetailsMvpView {

    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.pb_loading) CircularProgressBar mProgressBar;
    @Bind(R.id.iv_cover) ImageView mCoverImageView;
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

        Glide.with(this)
            .load(musician.getCovers().get(Musician.COVER_BIG))
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgressBar.setVisibility(View.GONE);
                    return false;
                }
            })
            .into(mCoverImageView);
    }

    /* Internals */
    // --------------------------------------------------------------------------------------------
    private void initView() {
        setContentView(R.layout.activity_music_details);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
