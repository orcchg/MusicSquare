package com.orcchg.musicsquare;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.orcchg.musicsquare.data.local.ByIdMusiciansSpecification;
import com.orcchg.musicsquare.data.local.MusiciansDatabase;
import com.orcchg.musicsquare.data.model.Musician;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class DatabaseTest extends AndroidTestCase {

    private MusiciansDatabase mDatabase;

    @BeforeClass
    public void initFixture() {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_com_orcchg_musicsquare_MusiciansDatabase");
        mDatabase = new MusiciansDatabase(context);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        List<Musician> musicians = new ArrayList<>();
        musicians.add(MockCreator.firstMusician());
        musicians.add(MockCreator.anyMusician());
        musicians.add(MockCreator.lastMusician());
        mDatabase.addMusicians(musicians);
    }

    @Override
    protected void tearDown() throws Exception {
        mDatabase.clear();
        mDatabase.close();
        super.tearDown();
    }

    @Test
    public void testReadDatabase() {
        List<Musician> musicians = new ArrayList<>();
        musicians.add(MockCreator.firstMusician());
        musicians.add(MockCreator.anyMusician());
        musicians.add(MockCreator.lastMusician());

        for (int i = 0; i < MockCreator.musicianIds().length; ++i) {
            final Musician musician = musicians.get(i);
            mDatabase.queryMusicians(new ByIdMusiciansSpecification(MockCreator.musicianIds()[i]))
                    .subscribe(new Observer<List<Musician>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(List<Musician> musicians) {
                            assertEquals(musicians.size(), 1);
                            assertEquals(musicians.get(0), musician);
                        }
                    });
        }
    }
}
