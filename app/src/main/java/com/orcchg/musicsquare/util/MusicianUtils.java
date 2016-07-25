package com.orcchg.musicsquare.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orcchg.musicsquare.data.model.Musician;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicianUtils {

    public static final long BAD_MUSICIAN_ID = -1;

    private static final String DELIMITER = ";";
    private static final String MAP_DELIMITER = "=";

    /**
     * Calculates a grade of a given {@link Musician} as a ratio between
     * {@link Musician#mTracksCount} and {@link Musician#mAlbumsCount}.
     * The more tracks and less albums a certain musician has the better
     * grade it has been given.
     *
     * @param musician Input model
     * @return grade of musician
     */
    public static int calculateGrade(@NonNull Musician musician) {
        float ratio = musician.getTracksCount() / (float) musician.getAlbumsCount();
        if (ratio > 10.0f) {
            return 10;
        }
        return (int) ratio;
    }

    // --------------------------------------------------------------------------------------------
    /**
     * Convert complex data into string representation and vice-versa.
     */
    @NonNull
    public static String genresToString(@NonNull Musician musician) {
        return TextUtils.join(DELIMITER, musician.getGenres());
    }

    @NonNull
    public static String coversToString(@NonNull Musician musician) {
        String delimiter = "";
        StringBuilder builder = new StringBuilder("");
        for (Map.Entry<String, String> entry : musician.getCovers().entrySet()) {
            builder.append(delimiter).append(entry.getKey()).append(MAP_DELIMITER).append(entry.getValue());
            delimiter = DELIMITER;
        }
        return builder.toString();
    }

    @NonNull
    public static List<String> stringToGenres(@NonNull String genres) {
        return Arrays.asList(genres.split(DELIMITER));
    }

    @NonNull
    public static Map<String, String> stringToCovers(@NonNull String covers) {
        String[] tokens = covers.split(DELIMITER);
        Map<String, String> map = new HashMap<>();
        for (String token : tokens) {
            String[] pair = token.split(MAP_DELIMITER);
            map.put(pair[0], pair[1]);
        }
        return map;
    }
}
