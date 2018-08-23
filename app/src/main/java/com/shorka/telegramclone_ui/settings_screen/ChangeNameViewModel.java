package com.shorka.telegramclone_ui.settings_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    String getUserFirstName(){
        return userRepo.getCurrUser().firstName;
    }

    String getUserLastName(){
        return userRepo.getCurrUser().lastName;
    }

    void updateUsername(@NonNull String firstName, @Nullable String lastName){
        User currUser = userRepo.getCurrUser();
        currUser.firstName = firstName;
        currUser.lastName = lastName;
        userRepo.updateUser(currUser);
    }
}
