package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;
import com.shorka.telegramclone_ui.utils.Config;
import com.shorka.telegramclone_ui.utils.ImageHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/22/2018.
 */
public class SettingsViewModel extends UserRepoViewModel {

    private static final String TAG = "SettingsViewModel";

    public SettingsViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application, userRepo);
    }

    User getCachedUser() {
        return userRepo.getCurrUser();
    }


    String saveImage(Bitmap bitmap) {

        return ImageHelper.saveImage(bitmap, ImageHelper.generateName(), getApplication());
    }

    void updatePicUrlOfCurrUser(String picUrl) {
        Log.d(TAG, "updatePicUrlOfCurrUser: ");
        User currUser = userRepo.getCurrUser();
        currUser.picUrl = picUrl;

        updateUser(currUser);
    }
}
