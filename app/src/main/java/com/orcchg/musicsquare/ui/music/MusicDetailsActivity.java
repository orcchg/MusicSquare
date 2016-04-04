package com.orcchg.musicsquare.ui.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
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
import com.orcchg.musicsquare.util.MusicianUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import timber.log.Timber;

public class MusicDetailsActivity extends BaseActivity<MusicDetailsPresenter> implements MusicDetailsMvpView {

    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.pb_loading) CircularProgressBar mProgressBar;
    @Bind(R.id.iv_cover) ImageView mCoverImageView;
    @Bind(R.id.tv_desription) TextView mDescriptionTextView;
    @Bind(R.id.tv_link) TextView mLinkTextView;

    @Bind(R.id.iv_star_1) ImageView mStarView_1;
    @Bind(R.id.iv_star_2) ImageView mStarView_2;
    @Bind(R.id.iv_star_3) ImageView mStarView_3;
    @Bind(R.id.iv_star_4) ImageView mStarView_4;
    @Bind(R.id.iv_star_5) ImageView mStarView_5;

    private Drawable mStrokeStar;
    private Drawable mHalfStar;
    private Drawable mFullStar;
    private List<View> mStarViews;

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
        initResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseResources();
    }

    /* View implementation */
    // --------------------------------------------------------------------------------------------
    @Override
    public void showMusicDetails(@NonNull Musician musician) {
        mCollapsingToolbar.setTitle(musician.getName());
        mToolbar.setTitle(musician.getName());
        mDescriptionTextView.setText(musician.getDescription());
        mLinkTextView.setText(musician.getLink());
        mLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());

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

        setGrade(musician);
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

    private void setGrade(@NonNull Musician musician) {
        int grade = MusicianUtils.calculateGrade(musician);
        Timber.d("Grade of " + musician.getName() + " is: " + grade);

        int quotient = grade / 2;
        int residual = grade % 2;

        for (int i = 0; i < quotient; ++i) {
            mStarViews.get(i).setBackgroundDrawable(mFullStar);
        }
        if (residual > 0) {
            mStarViews.get(quotient).setBackgroundDrawable(mHalfStar);
        }
        for (int i = quotient + residual; i < mStarViews.size(); ++i) {
            mStarViews.get(i).setBackgroundDrawable(mStrokeStar);
        }
    }

    // ------------------------------------------
    private void initResources() {
        mStarViews = new ArrayList<>();
        mStarViews.add(mStarView_1);
        mStarViews.add(mStarView_2);
        mStarViews.add(mStarView_3);
        mStarViews.add(mStarView_4);
        mStarViews.add(mStarView_5);

        Drawable strokeStar = getResources().getDrawable(R.drawable.ic_star_border_black_24dp);
        Drawable halfStar = getResources().getDrawable(R.drawable.ic_star_half_black_24dp);
        Drawable fullStar = getResources().getDrawable(R.drawable.ic_star_black_24dp);

        mStrokeStar = DrawableCompat.wrap(strokeStar);
        mHalfStar = DrawableCompat.wrap(halfStar);
        mFullStar = DrawableCompat.wrap(fullStar);

        @ColorInt int color = getResources().getColor(R.color.star_color);
        DrawableCompat.setTint(mStrokeStar, color);
        DrawableCompat.setTint(mHalfStar, color);
        DrawableCompat.setTint(mFullStar, color);
    }

    private void releaseResources() {
        for (int i = 0; i < mStarViews.size(); ++i) {
            mStarViews.set(i, null);
        }
        mStarViews.clear();

        DrawableCompat.unwrap(mStrokeStar);
        DrawableCompat.unwrap(mHalfStar);
        DrawableCompat.unwrap(mFullStar);
    }
}
