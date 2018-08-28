package com.shorka.telegramclone_ui.settings_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/22/2018.
 */
public class ChangeNameViewModel extends UserRepoViewModel {

    public ChangeNameViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application,userRepo);
    }

    String getUserFirstName(){
        return userRepo.getCurrUser().firstName;
    }

    String getUserLastName(){
        return userRepo.getCurrUser().lastName;
    }

    @SuppressLint("CheckResult")
    void updateUsername(@NonNull String firstName, @Nullable String lastName){
        User currUser = userRepo.getCurrUser();
        currUser.firstName = firstName;
        currUser.lastName = lastName;

        updateUser(currUser);
    }
}
