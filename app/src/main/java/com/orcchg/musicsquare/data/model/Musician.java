package com.orcchg.musicsquare.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of musician item in the data response.
 */
public class Musician {
    public static final String COVER_BIG = "big";
    public static final String COVER_SMALL = "small";

    @SerializedName("id") private final long mId;
    @SerializedName("name") private final String mName;
    @SerializedName("genres") private final List<String> mGenres;
    @SerializedName("tracks") private final int mTracksCount;
    @SerializedName("albums") private final int mAlbumsCount;
    @SerializedName("link") private final String mLink;
    @SerializedName("description") private final String mDescription;
    @SerializedName("cover") private final Map<String, String> mCovers;

    public Musician(Builder builder) {
        mId = builder.mId;
        mName = builder.mName;
        mGenres = builder.mGenres;
        mTracksCount = builder.mTracksCount;
        mAlbumsCount = builder.mAlbumsCount;
        mLink = builder.mLink;
        mDescription = builder.mDescription;
        mCovers = builder.mCovers;
    }

    public static class Builder {
        private final long mId;
        private final String mName;
        private List<String> mGenres = new ArrayList<>();
        private int mTracksCount;
        private int mAlbumsCount;
        private String mLink = "";
        private String mDescription = "";
        private Map<String, String> mCovers = new HashMap<>();

        public Builder(long id, @NonNull String name) {
            mId = id;
            mName = name;
        }

        public Builder setGenres(@NonNull List<String> genres) {
            mGenres = genres;
            return this;
        }

        public Builder setTracksCount(int count) {
            mTracksCount = count;
            return this;
        }

        public Builder setAlbumsCount(int count) {
            mAlbumsCount = count;
            return this;
        }

        public Builder setLink(@NonNull String link) {
            mLink = link;
            return this;
        }

        public Builder setDescription(@NonNull String description) {
            mDescription = description;
            return this;
        }

        public Builder setCovers(@NonNull Map<String, String> covers) {
            mCovers = covers;
            return this;
        }

        public Musician build() {
            return new Musician(this);
        }
    }

    /* Getters */
    // --------------------------------------------------------------------------------------------
    public long getId() { return mId; }
    public String getName() { return mName; }
    public List<String> getGenres() { return mGenres; }
    public int getTracksCount() { return mTracksCount; }
    public int getAlbumsCount() { return mAlbumsCount; }
    public String getLink() { return mLink; }
    public String getDescription() { return mDescription; }
    public Map<String, String> getCovers() { return mCovers; }

    /* Setters */
    // --------------------------------------------------------------------------------------------
    /* XXX: just left here for convenience
    public void setId(long mId) { this.mId = mId; }
    public void setName(String mName) { this.mName = mName; }
    public void setGenres(List<String> mGenres) { this.mGenres = mGenres; }
    public void setTracksCount(int mTracksCount) { this.mTracksCount = mTracksCount; }
    public void setAlbumsCount(int mAlbumsCount) { this.mAlbumsCount = mAlbumsCount; }
    public void setLink(String mLink) { this.mLink = mLink; }
    public void setDescription(String mDescription) { this.mDescription = mDescription; }
    public void setCovers(Map<String, String> mCovers) { this.mCovers = mCovers; }
    */
}
