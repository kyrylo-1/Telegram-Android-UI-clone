package com.shorka.telegramclone_ui.settings_screen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/14/2018.
 */
public class ChangeNamePresenter  {

    private static final String TAG = "ChangeNamePresenter";
    @NonNull
    private final UserRepository userRepo;
    @NonNull
    private final ChangeNameContract.View view;
    private User user;

    public ChangeNamePresenter(@NonNull UserRepository userRepo, @NonNull ChangeNameContract.View view) {

        this.userRepo = userRepo;
        this.view = view;
//        user = userRepo.getCurrLiveUser();
    }


    public void updateName(@Nullable String firstName, @Nullable String lastName) {

//        Log.d(TAG, "updateName: ");
//        if (firstName == null || firstName.isEmpty()) {
//            view.returnToPreviousScreen();
//            return;
//        }
//        user.name = firstName;
//        userRepo.updateUser(user);
//        view.returnToPreviousScreen();
    }


    public void loadName() {

//        user = userRepo.getCurrLiveUser();
//        Log.d(TAG, "loadName: user:" + user.name);
//        view.showName(user.name, user.name);
    }
}
