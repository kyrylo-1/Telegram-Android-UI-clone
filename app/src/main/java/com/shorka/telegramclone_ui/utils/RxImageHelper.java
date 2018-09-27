package com.shorka.telegramclone_ui.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Created by Kyrylo Avramenko on 9/26/2018.
 */
public class RxImageHelper {

    public static Completable addToGallery(@NonNull final String path, final Context context){
        return Completable.fromAction(() ->
                ImageHelper.addToGallery(path, context,
                        ImageHelper.getRotation(path)));
    }
}
