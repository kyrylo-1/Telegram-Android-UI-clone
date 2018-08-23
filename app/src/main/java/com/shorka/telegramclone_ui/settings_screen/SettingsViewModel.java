package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/22/2018.
 */
public class SettingsViewModel  extends AndroidViewModel {

    private static final String TAG = "SettingsViewModel";
    private UserRepository userRepo;
    public SettingsViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        this.userRepo = userRepo;
    }

    User getCachedUser(){
        return userRepo.getCurrUser();
    }


}
