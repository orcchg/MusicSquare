package com.orcchg.musicsquare.data.local;

/**
 * Represents the schema in the {@link MusiciansDatabase}.
 */
class MusicContract {

    MusicContract() {
        // protect from accidental instantiation
    }

    static class MusiciansTable {
        static final String TABLE_NAME = "Musicians";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_GENRES = "genres";
        static final String COLUMN_NAME_TRACKS_COUNT = "tracks_count";
        static final String COLUMN_NAME_ALBUMS_COUNT = "albums_count";
        static final String COLUMN_NAME_LINK = "link";
        static final String COLUMN_NAME_DESCRIPTION = "description";
        static final String COLUMN_NAME_COVERS = "covers";
    }

    static final String CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + MusiciansTable.TABLE_NAME + " (" +
            MusiciansTable.COLUMN_NAME_ID + " INTEGER UNIQUE, " +
            MusiciansTable.COLUMN_NAME_NAME + " TEXT DEFAULT \"\", " +
            MusiciansTable.COLUMN_NAME_GENRES + " TEXT DEFAULT \"\", " +
            MusiciansTable.COLUMN_NAME_TRACKS_COUNT + " INTEGER DEFAULT 0, " +
            MusiciansTable.COLUMN_NAME_ALBUMS_COUNT + " INTEGER DEFAULT 0, " +
            MusiciansTable.COLUMN_NAME_LINK + " TEXT DEFAULT \"\", " +
            MusiciansTable.COLUMN_NAME_DESCRIPTION + " TEXT DEFAULT \"\", " +
            MusiciansTable.COLUMN_NAME_COVERS + " TEXT DEFAULT \"\");";

    static final String CLEAR_TABLE_STATEMENT =
            "DROP TABLE IF EXISTS " + MusiciansTable.TABLE_NAME;

    static final String READ_ALL_STATEMENT =
            "SELECT * FROM " + MusiciansTable.TABLE_NAME;

    static final String READ_STATEMENT = READ_ALL_STATEMENT + " WHERE %s ";

    static final String INSERT_STATEMENT =
            "INSERT OR REPLACE INTO " + MusiciansTable.TABLE_NAME + " (" +
                    MusiciansTable.COLUMN_NAME_ID + ", " +
                    MusiciansTable.COLUMN_NAME_NAME + ", " +
                    MusiciansTable.COLUMN_NAME_GENRES + ", " +
                    MusiciansTable.COLUMN_NAME_TRACKS_COUNT + ", " +
                    MusiciansTable.COLUMN_NAME_ALBUMS_COUNT + ", " +
                    MusiciansTable.COLUMN_NAME_LINK + ", " +
                    MusiciansTable.COLUMN_NAME_DESCRIPTION + ", " +
                    MusiciansTable.COLUMN_NAME_COVERS + ") " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

    static final String DELETE_ALL_STATEMENT = "DELETE FROM " + MusiciansTable.TABLE_NAME + ";";

    static final String DELETE_STATEMENT =
            "DELETE FROM " + MusiciansTable.TABLE_NAME + " WHERE %s;";
}
