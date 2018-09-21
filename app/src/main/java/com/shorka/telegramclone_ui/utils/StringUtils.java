package com.shorka.telegramclone_ui.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kyrylo Avramenko on 9/5/2018.
 */
public class StringUtils {

    public static String capitalize(String str){
        return new StringBuilder().append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
    }

    @IntDef({FontType.NORMAL, FontType.BOLD, FontType.ITALIC, FontType.MONO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontType {
        int NORMAL = 0;
        int BOLD = 1;
        int ITALIC = 2;
        int MONO = 3;
    }
}
