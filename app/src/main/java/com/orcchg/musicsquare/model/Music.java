package com.orcchg.musicsquare.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Model of musician item in the data response.
 */
public class Music {
    @SerializedName("id") private long mId;
    @SerializedName("name") private String mName;
    @SerializedName("genres") private List<String> mGenres;
    @SerializedName("tracks") private int mTracksCount;
    @SerializedName("albums") private int mAlbumsCount;
    @SerializedName("link") private String mLink;
    @SerializedName("description") private String mDescription;
    @SerializedName("cover") private Map<String, String> mCovers;

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
