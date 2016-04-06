package com.orcchg.musicsquare;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.orcchg.musicsquare.ui.music.MusicListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MusicListActivityTest {

    @Rule
    public ActivityTestRule<MusicListActivity> mActivityRule = new ActivityTestRule<>(MusicListActivity.class);

    @Test
    public void listItemClickShouldOpenDetailsActivity() {

    }
}
