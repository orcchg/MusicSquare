package com.orcchg.musicsquare.ui.music;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.model.Musician;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for {@link android.support.v7.widget.RecyclerView}
 * of {@link com.orcchg.musicsquare.data.model.Musician} items.
 */
public class MusiciansAdapter extends RecyclerView.Adapter<MusiciansAdapter.MusiciansViewHolder> {

    private List<Musician> mMusicians;

    public static class MusiciansViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_musician_title) TextView mTitleView;

        public MusiciansViewHolder(View view) {
            super(view);
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
    public void onBindViewHolder(MusiciansViewHolder holder, int position) {
        Musician item = mMusicians.get(position);
        holder.mTitleView.setText(item.getName());
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
