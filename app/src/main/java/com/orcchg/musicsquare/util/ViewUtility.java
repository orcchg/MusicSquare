package com.orcchg.musicsquare.util;

import android.content.Context;

import com.orcchg.musicsquare.R;

public class ViewUtility {

    public static boolean isLargeScreen(Context context) {
        return context.getResources().getBoolean(R.bool.isLargeScreen);
    }
}
