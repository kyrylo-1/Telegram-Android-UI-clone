package com.shorka.telegramclone_ui.chats_previews_screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.db.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
public class ChatPreviewPresenter implements ChatPreviewContract.UserActionsListener {

    private static final String TAG = "ChatPreviewPresenter";

    @NonNull
    private ChatPreviewContract.View chatView;

    @NonNull
    private UserRepository userRepo;


    public ChatPreviewPresenter(@NonNull UserRepository userRepo, @NonNull ChatPreviewContract.View chatView) {
        this.userRepo = userRepo;
        this.chatView = chatView;
    }

    @Override
    public void loadChats() {
        userRepo.getSingleCurrUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Log.d(TAG, "accept: " + user.phoneNumber + " \n bio: " + user.bio);
                    userRepo.setCurrUser(user);
                    chatView.updateUserDetail(user);
                });
    }

    @Override
    public void writeNewMessage() {

    }

    @Override
    public void openChatMessages(long idRecipient) {

    }

}
