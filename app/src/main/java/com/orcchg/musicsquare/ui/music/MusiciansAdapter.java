package com.orcchg.musicsquare.ui.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.util.MusicianUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Adapter for {@link android.support.v7.widget.RecyclerView}
 * of {@link com.orcchg.musicsquare.data.model.Musician} items.
 *
 * Fetches data from {@link com.orcchg.musicsquare.data.local.MusiciansDatabase}
 * using the acceleration trick {@see https://habrahabr.ru/post/154931/}.
 */
class MusiciansAdapter extends RecyclerView.Adapter<MusiciansAdapter.MusiciansViewHolder> {

    private List<Musician> mMusicians;

    static class MusiciansViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private View mGridItemView;  // only on large screens
        @Bind(R.id.pb_loading) CircularProgressBar mProgressBar;
        @Bind(R.id.iv_cover) ImageView mIconView;
        @Bind(R.id.tv_musician_title) TextView mTitleView;

        MusiciansViewHolder(View view) {
            super(view);
            mView = view;
            mGridItemView = view.findViewById(R.id.fl_grid_item);
            ButterKnife.bind(this, view);
        }
    }

    MusiciansAdapter() {
        mMusicians = new ArrayList<>();
    }

    @Override
    public MusiciansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_musician, parent, false);
        return new MusiciansViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MusiciansViewHolder holder, int position) {
        final Context context = holder.mView.getContext();
        final Musician musician = mMusicians.get(position);

        holder.mView.setOnClickListener((view) -> MusicianUtils.openDetailsScreen(context, musician.getId()));

        if (holder.mGridItemView != null) {
            holder.mGridItemView.setOnClickListener((view) ->
                // this listener is necessary in order to enable foreground to show
                MusicianUtils.openDetailsScreen(context, musician.getId()));
        }

        holder.mTitleView.setText(musician.getName());

        Glide.with(context)
            .load(musician.getCovers().get(Musician.COVER_SMALL))
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.mProgressBar.setVisibility(View.GONE);
                    return false;
                }
            })
            .into(holder.mIconView);
    }

    @Override
    public int getItemCount() {
        return mMusicians.size();
    }

    void setMusicians(List<Musician> posts) {
        if (posts != null && !posts.isEmpty()) {
            mMusicians.clear();
            mMusicians.addAll(posts);
            notifyDataSetChanged();
        }
    }
}
