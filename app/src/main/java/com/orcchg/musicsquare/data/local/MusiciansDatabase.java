package com.orcchg.musicsquare.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.util.MusicianUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Cache that stores {@link com.orcchg.musicsquare.data.model.Musician}
 * models locally.
 */
public class MusiciansDatabase extends SQLiteOpenHelper implements MusiciansRepository {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "com_orcchg_musicsquare_MusiciansDatabase.db";

    public MusiciansDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MusicContract.CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MusicContract.CLEAR_TABLE_STATEMENT);
        onCreate(db);
    }

    public boolean isEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MusicContract.READ_ALL_STATEMENT, null);
        boolean result = true;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0) == 0;
        }
        cursor.close();
        return result;
    }

    /* Repository operations */
    // --------------------------------------------------------------------------------------------
    @Override
    public void addMusicians(List<Musician> musicians) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        SQLiteStatement insert = db.compileStatement(MusicContract.INSERT_STATEMENT);

        /**
         * Insert multiple rows in one transaction (optimization)
         */
        for (Musician musician : musicians) {
            String link = musician.getLink();
            String description = musician.getDescription();

            insert.bindLong(1, musician.getId());
            insert.bindString(2, musician.getName());
            insert.bindString(3, MusicianUtils.genresToString(musician));
            insert.bindLong(4, musician.getTracksCount());
            insert.bindLong(5, musician.getAlbumsCount());
            insert.bindString(6, link == null ? "" : link);  // could be null
            insert.bindString(7, description == null ? "" : description);  // could be null
            insert.bindString(8, MusicianUtils.coversToString(musician));
            insert.execute();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void updateMusicians(List<Musician> musicians) {
        addMusicians(musicians);  // using REPLACE clause
    }

    @Override
    public void removeMusicians(MusiciansSpecification specification) {
        String statement = specification == null ? MusicContract.DELETE_ALL_STATEMENT : String.format(MusicContract.DELETE_STATEMENT, specification.getSelectionArgs());

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        SQLiteStatement delete = db.compileStatement(statement);
        delete.execute();
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public Observable<List<Musician>> getAllMusicians() {
        return queryMusicians(null);
    }

    @Override
    public Observable<List<Musician>> queryMusicians(MusiciansSpecification specification) {
        final String statement = specification == null ? MusicContract.READ_ALL_STATEMENT : String.format(MusicContract.READ_STATEMENT, specification.getSelectionArgs());

        final SQLiteDatabase db = getReadableDatabase();

        final Callable<List<Musician>> function = new Callable<List<Musician>>() {
            @Override
            public List<Musician> call() throws Exception {
                Cursor cursor = db.rawQuery(statement, null);
                List<Musician> musicians = new ArrayList<>();
                while (cursor.moveToNext()) {
                    musicians.add(MusicianFactory.create(cursor));
                }
                cursor.close();
                return musicians;
            }
        };

        return Observable.create(new Observable.OnSubscribe<List<Musician>>() {
            @Override
            public void call(Subscriber<? super List<Musician>> subscriber) {
                try {
                    subscriber.onNext(function.call());
                } catch (Exception e) {
                    Timber.e("Error reading from the database: %s", e.toString());
                }
            }
        });
    }

    /* Factory */
    // --------------------------------------------------------------------------------------------
    private static class MusicianFactory {
        static Musician create(Cursor cursor) {
            long id = cursor.getInt(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_ID));
            String name = cursor.getString(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_NAME));
            String genres = cursor.getString(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_GENRES));
            int tracksCount = cursor.getInt(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_TRACKS_COUNT));
            int albumsCount = cursor.getInt(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_ALBUMS_COUNT));
            String link = cursor.getString(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_LINK));
            String description = cursor.getString(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_DESCRIPTION));
            String covers = cursor.getString(cursor.getColumnIndex(MusicContract.MusiciansTable.COLUMN_NAME_COVERS));

            return new Musician.Builder(id, name)
                    .setGenres(MusicianUtils.stringToGenres(genres))
                    .setTracksCount(tracksCount)
                    .setAlbumsCount(albumsCount)
                    .setLink(link)
                    .setDescription(description)
                    .setCovers(MusicianUtils.stringToCovers(covers))
                    .build();
        }
    }
}
