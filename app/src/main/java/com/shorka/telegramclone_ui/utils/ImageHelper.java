package com.shorka.telegramclone_ui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 9/17/2018.
 */
public class ImageHelper {

    private static final String TAG = "ImageHelper";
    private static final String APP_TAG = "TelegramClone";

    public static File createImageFile(Context context) {

        File storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        if (!storageDir.exists() && !storageDir.mkdirs())
            Log.d(TAG, "failed to create directory");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";
        return new File(storageDir.getPath() + File.separator + fileName);
    }

    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;

        return Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);
    }


    public static void addToGallery(@NonNull String picPath, final Context context, int rotation) {
        Log.d(TAG, "addToGallery: ");

        ContentValues values = new ContentValues(1);

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, picPath);
        values.put(MediaStore.Images.Media.ORIENTATION, rotation);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static Flowable<String> saveBitmap(Context context, Bitmap bitmap) {

        Log.d(TAG, "saveBitmap: ");
        File file = createImageFile(context);

        return Flowable.fromCallable(() -> {

            FileOutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            return file.getAbsolutePath();
        }).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;

        return false;
    }

    public static int getRotation(@NonNull final String picPath) {

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(picPath);
            final int orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Log.d(TAG, "getRotation: " + orientation);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return  90;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    return  180;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    return  270;

                default:
                    return  0;
            }

        } catch (IOException e) {
            Log.e(TAG, "getRotation: " + e);
        }

        Log.d(TAG, "getRotation: return rotation 0");
        return 0;
    }
}
