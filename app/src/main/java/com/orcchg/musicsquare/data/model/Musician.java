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

    /* Equals and hash code */
    // --------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Musician musician = (Musician) o;

        if (mId != musician.mId) return false;
        if (mTracksCount != musician.mTracksCount) return false;
        if (mAlbumsCount != musician.mAlbumsCount) return false;
        if (mName != null ? !mName.equals(musician.mName) : musician.mName != null) return false;
        if (mGenres != null ? !mGenres.equals(musician.mGenres) : musician.mGenres != null)
            return false;
        if (mLink != null ? !mLink.equals(musician.mLink) : musician.mLink != null) return false;
        if (mDescription != null ? !mDescription.equals(musician.mDescription) : musician.mDescription != null)
            return false;
        return !(mCovers != null ? !mCovers.equals(musician.mCovers) : musician.mCovers != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (mId >>> 32));
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mGenres != null ? mGenres.hashCode() : 0);
        result = 31 * result + mTracksCount;
        result = 31 * result + mAlbumsCount;
        result = 31 * result + (mLink != null ? mLink.hashCode() : 0);
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        result = 31 * result + (mCovers != null ? mCovers.hashCode() : 0);
        return result;
    }
}
