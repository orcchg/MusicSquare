package com.orcchg.musicsquare.data.local;

import com.orcchg.musicsquare.data.model.Musician;

import java.util.List;

import rx.Observable;

/**
 * Common operations with the storage of {@link com.orcchg.musicsquare.data.model.Musician} items.
 */
public interface MusiciansRepository {

    void addMusicians(List<Musician> posts);
    void updateMusicians(List<Musician> posts);
    void removeMusicians(List<Musician> posts);

    Observable<List<Musician>> getAllMusicians();
    Observable<List<Musician>> queryMusicians(MusiciansSpecification specification);
}
