package com.orcchg.musicsquare.util;

import android.support.annotation.NonNull;

import com.orcchg.musicsquare.data.model.Musician;

public class MusicUtils {

    /**
     * Calculates a grade of a given {@link Musician} as a ratio between
     * {@link Musician#mTracksCount} and {@link Musician#mAlbumsCount}.
     * The more tracks and less albums a certain musician has the better
     * grade it has been given.
     *
     * @param musician
     * @return
     */
    public static int calculateGrade(@NonNull Musician musician) {
        float ratio = musician.getTracksCount() / (float) musician.getAlbumsCount();
        if (ratio > 10.0f) {
            return 10;
        }
        return (int) ratio;
    }
}
