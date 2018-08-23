package com.shorka.telegramclone_ui.chats_previews_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;
import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class ChatPreviewViewModel extends AndroidViewModel {

    private static final String TAG = "ChatPreviewViewModel";
    private UserRepository userRepo;

    public ChatPreviewViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        this.userRepo = userRepo;
    }

    public LiveData<User> getLiveCurrUser() {
        return userRepo.getCurrLiveUser();
    }

    public LiveData<List<Message>> getAllLiveMessages() {
        return userRepo.getAllLiveMessages();
    }


    public LiveData<List<User>> getAllLiveUsers() {
        return userRepo.getAllLiveUsers();
    }

    List<User> allUsers() {
        return userRepo.getAllUsers();
    }

    void setAllUsers(List<User> allUsers) {
        userRepo.setAllUsers(allUsers);
    }

    public List<MessagePreview> transformToMsgPreviews(List<Message> listMessages) {

        Log.d(TAG, "transformToMsgPreviews: ");
        List<MessagePreview> listPreview = new ArrayList<>();
        for (Message msg : listMessages) {
            if (msg != null)
                listPreview.add(transformToMsgPreview(msg));

            else
                Log.e(TAG, "transformToMsgPreviews: userMsg is NULL");
        }

        return listPreview;
    }

    void cacheUser(User user) {
        userRepo.setCurrUser(user);
    }

    User getCacheUser() {
        return userRepo.getCurrUser();
    }

    public LiveData<List<Message>> getRecentMessageByChat(){
        return userRepo.getRecentMessageByChat();
    }

    private MessagePreview transformToMsgPreview(Message msg) {

        User user = userRepo.getCachedUserById(msg.recipientId);
        if (user == null)
            return null;

        return new MessagePreview.MessagePreviewBuilder()
                .withId(user.getId())
                .withContactName(user.name)
                .withLastMessage(msg.text)
                .withIsPinned(false)
                .withIsReaded(true)
                .withDate("66:66")
                .withImageResId(R.drawable.kochek_withback)
                .buildMesagePreview();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
    }
}
