package com.shorka.telegramclone_ui.utils;

import android.app.DownloadManager;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class Config {

    public final static String USER_ID_EXTRA = "USER_ID";

    public static class Requests{
        public final static int REQUEST_CAPTURE_IMAGE = 1337;
        public final static int GALLERY_REQUEST = 1338;
    }

    public static class Intents{
        public final static String IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE";
    }
}
