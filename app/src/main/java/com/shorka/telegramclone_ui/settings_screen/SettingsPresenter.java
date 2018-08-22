package com.shorka.telegramclone_ui.settings_screen;

import android.support.annotation.NonNull;
import android.util.Log;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/13/2018.
 */
public class SettingsPresenter {

    private static final String TAG = "SettingsPresenter";
    @NonNull
    private final UserRepository userRepo;


    public SettingsPresenter(@NonNull UserRepository userRepo, @NonNull SettingsContract.View settView) {

        this.userRepo = userRepo;
    }



    public void loadUserInfo() {

//        if(userRepo.getCurrLiveUser()!= null){
//            settView.showUserInfo(userRepo.getCurrLiveUser());
//        }
//
//        else Log.e(TAG, "loadUserInfo: getCurrLiveUser() CAN NOT be NULL");

    }


}
