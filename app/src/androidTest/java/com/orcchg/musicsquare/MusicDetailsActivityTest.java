package com.orcchg.musicsquare;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.orcchg.musicsquare.ui.music.MusicDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MusicDetailsActivityTest {

    @Rule
    public ActivityTestRule<MusicDetailsActivity> rule = new ActivityTestRule(MusicDetailsActivity.class, true, false);

    @Test
    public void openDetailsActivity() {

    }
}
