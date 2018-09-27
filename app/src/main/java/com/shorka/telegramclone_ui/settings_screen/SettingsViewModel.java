package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;
import com.shorka.telegramclone_ui.utils.RxImageHelper;

import io.reactivex.Completable;
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


    void updatePicUrlOfCurrUser(String picUrl) {
        Log.d(TAG, "updatePicUrlOfCurrUser: ");
        User currUser = userRepo.getCurrUser();
        currUser.picUrl = picUrl;

        updateUser(currUser);
    }

    Completable addToGallery(@NonNull final String path, final Context context){
        return RxImageHelper.addToGallery(path, context)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());
    }

}
