package com.shorka.telegramclone_ui.contact_chat_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class ContactChatViewModel extends AndroidViewModel {

    private static final String TAG = "ContactChatViewModel";
    private UserRepository userRepo;
    private Context context;

    public ContactChatViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        Log.d(TAG, "ContactChatViewModel: init");
        context = application;
        this.userRepo = userRepo;
    }

    public User getUser(long id) {
        return userRepo.getCachedUserById(id);
    }

    public LiveData<List<Message>> getListMessages(long recipientId){
        return userRepo.getMessagesyRecipientId(recipientId);
    }
}
