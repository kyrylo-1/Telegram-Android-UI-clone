package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/22/2018.
 */
public class SettingsViewModel  extends UserRepoViewModel {

    private static final String TAG = "SettingsViewModel";

    public SettingsViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application, userRepo);
    }

    User getCachedUser(){
        return userRepo.getCurrUser();
    }


}
