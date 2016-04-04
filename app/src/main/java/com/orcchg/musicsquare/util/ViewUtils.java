package com.orcchg.musicsquare.util;

import android.content.Context;

import com.orcchg.musicsquare.R;

public class ViewUtils {

    public static boolean isLargeScreen(Context context) {
        return context.getResources().getBoolean(R.bool.isLargeScreen);
    }
}
