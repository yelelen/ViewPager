package com.yll.viewpager;

import android.content.Context;

/**
 * Created by yelelen on 7/11/2017.
 */

public class DensityUtils {
    public static int dp2px(Context context, int dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    public static int px2dp(Context context, int px){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(px / scale + 0.5f);
    }

}
