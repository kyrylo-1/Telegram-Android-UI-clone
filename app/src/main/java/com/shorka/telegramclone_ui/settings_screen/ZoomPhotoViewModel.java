package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.UserRepository;
import com.shorka.telegramclone_ui.utils.ImageHelper;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 9/25/2018.
 */
public class ZoomPhotoViewModel extends UserRepoViewModel {

    private String currPicPath;

    public ZoomPhotoViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application, userRepo);
    }
    public String getCurrPicPath() {
        return currPicPath;
    }

    public void setCurrPicPath(String currPicPath) {
        this.currPicPath = currPicPath;
    }


    public boolean addToGallery() {
//        ImageHelper.addToGallery(currPicPath, getApplication(), ImageHelper.getRotation(currPicPath));
        return true;
    }
}
