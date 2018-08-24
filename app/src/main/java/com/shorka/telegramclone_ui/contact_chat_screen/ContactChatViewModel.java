package com.shorka.telegramclone_ui.contact_chat_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class ContactChatViewModel extends AndroidViewModel {

    private static final String TAG = "ContactChatViewModel";
    private UserRepository userRepo;
    private Context context;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public ContactChatViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        Log.d(TAG, "ContactChatViewModel: init");
        context = application;
        this.userRepo = userRepo;
    }

    public User getUser(long id) {
        return userRepo.getCachedUserById(id);
    }

    public LiveData<List<Message>> getListMessages(long recipientId) {
        return userRepo.getMessagesyRecipientId(recipientId);
    }

    @SuppressLint("CheckResult")
    public void sendMessage(long recipientId, String text) {
        Log.d(TAG, "sendMessage with text: " + text);
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = text;
        m.date = "66:66";
        m.messageType = Message.SENT;

        Consumer<Message> consumer = message -> userRepo.insertMessage(message);
        Flowable.just(m)
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, Throwable::printStackTrace);

    }
}
