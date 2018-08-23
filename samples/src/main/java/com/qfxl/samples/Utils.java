package com.qfxl.samples;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
    }
}
