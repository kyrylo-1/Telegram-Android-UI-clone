package com.shorka.telegramclone_ui.utils;

import android.os.Build;
import android.support.annotation.IntDef;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kyrylo Avramenko on 9/5/2018.
 */
public class StringUtils {

    private static final String TAG = "StringUtils";

    public static String capitalize(String str) {
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

    public static String toCustomHtml(Spanned spanned) {

        String htmlText = Html.toHtml(spanned);
        if (TextUtils.isEmpty(htmlText))
            return htmlText;

        return htmlText.replace("<p dir=\"ltr\">", "")
                .replace("</p>", "")
                .replace("<u>", "")
                .replace("</u>>", "")
                .trim();
    }

    /**
     * Calling {@link Html#fromHtml(String)} with consideration of Android SDK versions
     */
    public static Spanned fromHtml(String text) {

        return Build.VERSION.SDK_INT >= 24 ?
                Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY) :
                Html.fromHtml(text);
    }

    public static String clearAllHtmlTags(String text) {
        return Html.fromHtml(text).toString();
    }
}
