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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class ContactChatViewModel extends AndroidViewModel {

    private static final String TAG = "ContactChatViewModel";
    private UserRepository userRepo;
    private Context context;
    private final List<Message> messagesToInsert;

    public ContactChatViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        Log.d(TAG, "ContactChatViewModel: init");
        context = application;
        this.userRepo = userRepo;
        messagesToInsert = new ArrayList<>();
    }

    public User getUser(long id) {
        return userRepo.getCachedUserById(id);
    }

    public LiveData<List<Message>> getListMessages(long recipientId){
        return userRepo.getMessagesyRecipientId(recipientId);
    }

    public void sendMessage(long recipientId, String text){
        Log.d(TAG, "sendMessage with text: " + text);
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = text;
        m.date = "66:66";
        m.messageType = Message.SENT;
        
        messagesToInsert.add(m);
        userRepo.insertMessage(m);
    }
}
