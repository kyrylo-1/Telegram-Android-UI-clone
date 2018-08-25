package com.shorka.telegramclone_ui.contact_chat_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.db.LocalDatabase;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class ContactChatViewModel extends AndroidViewModel {

    private static final String TAG = "ContactChatViewModel";

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final LocalDatabase localDb;
    public ContactChatViewModel(@NonNull Application application, LocalDatabase localDb) {
        super(application);
        Log.d(TAG, "ContactChatViewModel: init");
        this.localDb = localDb;
    }

    public User getUser(long id) {
        return localDb.getUserRepo().getCachedUserById(id);
    }

    public LiveData<List<Message>> getListMessages(long recipientId) {
        return localDb.getMessageRepo().getMessagesyRecipientId(recipientId);
    }

    @SuppressLint("CheckResult")
    public void sendMessage(long recipientId, String text) {
        Log.d(TAG, "sendMessage with text: " + text);
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = text;
        m.date = "66:66";
        m.messageType = Message.SENT;

        Consumer<Message> consumer = localDb.getMessageRepo()::insertMessage;
        Flowable.just(m)
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, Throwable::printStackTrace);

    }
}
