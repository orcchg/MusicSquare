package com.orcchg.musicsquare.ui.music;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Adapter for {@link android.support.v7.widget.RecyclerView}
 * of {@link com.orcchg.musicsquare.data.model.Musician} items.
 */
public class MusiciansAdapter extends RecyclerView.Adapter<MusiciansAdapter.MusiciansViewHolder> {

    private List<Musician> mMusicians;

    public static class MusiciansViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        @Bind(R.id.pb_loading) CircularProgressBar mProgressBar;
        @Bind(R.id.iv_cover) ImageView mIconView;
        @Bind(R.id.tv_musician_title) TextView mTitleView;

        public MusiciansViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

    public MusiciansAdapter() {
        mMusicians = new ArrayList<>();
    }

    @Override
    public MusiciansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_musician, parent, false);
        MusiciansViewHolder holder = new MusiciansViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MusiciansViewHolder holder, int position) {
        final Context context = holder.mView.getContext();
        final Musician musician = mMusicians.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MusicDetailsActivity.getIntent(context, musician);
                context.startActivity(intent);
            }
        });

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

    public void setMusicians(List<Musician> posts) {
        if (posts != null && !posts.isEmpty()) {
            mMusicians.clear();
            mMusicians.addAll(posts);
            notifyDataSetChanged();
        }
    }
}
