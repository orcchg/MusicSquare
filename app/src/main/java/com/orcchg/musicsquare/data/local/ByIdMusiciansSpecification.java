package com.orcchg.musicsquare.data.local;

import android.support.annotation.NonNull;

import com.orcchg.musicsquare.data.model.Musician;

/**
 * Specification allows to query {@link com.orcchg.musicsquare.data.model.Musician} items
 * from the storage by {@link com.orcchg.musicsquare.data.model.Musician#mId} value.
 */
public class ByIdMusiciansSpecification implements MusiciansSpecification {
    private final long mId;

    public ByIdMusiciansSpecification(long id) {
        mId = id;
    }

    @Override
    public boolean specified(@NonNull Musician musician) {
        return musician.getId() == mId;
    }

    @Override
    public String getSelectionArgs() {
        return MusicContract.MusiciansTable.COLUMN_NAME_ID + " = " + Long.toString(mId);
    }
}
