package com.shorka.telegramclone_ui.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Kyrylo Avramenko on 6/21/2018.
 */
public class Utils {

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
