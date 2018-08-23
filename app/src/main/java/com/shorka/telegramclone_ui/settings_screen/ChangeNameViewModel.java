package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/22/2018.
 */
public class ChangeNameViewModel extends AndroidViewModel {

    private UserRepository userRepo;

    public ChangeNameViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        this.userRepo = userRepo;
    }

    String getUserName(){
        return userRepo.getCurrUser().name;
    }

    void updateUsername(String name){
        User currUser = userRepo.getCurrUser();
        currUser.name = name;
        userRepo.updateUser(currUser);
    }
}
