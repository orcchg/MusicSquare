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
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
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
import com.orcchg.musicsquare.util.ViewUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import timber.log.Timber;

public class MusicDetailsActivity extends BaseActivity<MusicDetailsPresenter> implements MusicDetailsMvpView {

    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.pb_loading) CircularProgressBar mProgressBar;
    @Bind(R.id.iv_cover) ImageView mCoverImageView;
    @Bind(R.id.top_overlay) View mTopOverlayView;
    @Bind(R.id.bottom_overlay) View mBottomOverlayView;
    @Bind(R.id.tv_desription) TextView mDescriptionTextView;
    @Bind(R.id.tv_link) TextView mLinkTextView;
    @Bind(R.id.tv_genres) TextView mGenresTextView;
    @Bind(R.id.tv_tracks_count) TextView mTracksCountTextView;
    @Bind(R.id.tv_albums_count) TextView mAlbumsCountTextView;

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
        if (mCollapsingToolbar != null) { mCollapsingToolbar.setTitle(musician.getName()); }
        mToolbar.setTitle(musician.getName());
        mDescriptionTextView.setText(musician.getDescription());
        mLinkTextView.setText(musician.getLink());
        mLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mGenresTextView.setText(TextUtils.join(", ", musician.getGenres()));
        mTracksCountTextView.setText(String.format(getResources().getString(R.string.str_tracks_count), Integer.toString(musician.getTracksCount())));
        mAlbumsCountTextView.setText(String.format(getResources().getString(R.string.str_albums_count), Integer.toString(musician.getAlbumsCount())));

        startProgressAnimation();

        Glide.with(this)
            .load(musician.getCovers().get(Musician.COVER_BIG))
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    // TODO: handle error
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    stopProgressAnimation();
                    return false;
                }
            })
            .into(mCoverImageView);

        setGrade(musician);
    }

    /* Internals */
    // --------------------------------------------------------------------------------------------
    private void initView() {
        if (ViewUtility.isLargeScreen(this)) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  // removes title from Activity as Dialog
        }
        setContentView(R.layout.activity_music_details);
        ButterKnife.bind(this);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);  // disabled on tablets
    }

    private void initToolbar() {
        if (mCollapsingToolbar != null) {
            mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
            mCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        }
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

    /* Misc */
    // --------------------------------------------------------------------------------------------
    private void startProgressAnimation() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTopOverlayView.setVisibility(View.INVISIBLE);
        mBottomOverlayView.setVisibility(View.INVISIBLE);
    }

    private void stopProgressAnimation() {
        mProgressBar.setVisibility(View.GONE);
        mTopOverlayView.setVisibility(View.VISIBLE);
        mBottomOverlayView.setVisibility(View.VISIBLE);
    }
}
