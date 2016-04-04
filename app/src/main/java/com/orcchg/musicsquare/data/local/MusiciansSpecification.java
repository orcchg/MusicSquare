package com.orcchg.musicsquare.data.local;

import android.support.annotation.NonNull;

import com.orcchg.musicsquare.data.model.Musician;

/**
 * Filtering interface to query certain items from the storage.
 */
public interface MusiciansSpecification {

    boolean specified(@NonNull Musician post);

    String getSelectionArgs();
}
