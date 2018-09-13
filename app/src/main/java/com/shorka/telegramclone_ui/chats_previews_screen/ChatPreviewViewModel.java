package com.shorka.telegramclone_ui.chats_previews_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.MessagePreview;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.LocalDatabase;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class ChatPreviewViewModel extends AndroidViewModel {

    private static final String TAG = "ChatPreviewViewModel";
    private final LocalDatabase localDb;
    private CompositeDisposable compDisposable;

    public ChatPreviewViewModel(@NonNull Application application, LocalDatabase localDb) {
        super(application);
        this.localDb = localDb;
        compDisposable = new CompositeDisposable();
    }

    public LiveData<User> getLiveCurrUser() {
        return localDb.getUserRepo().getCurrLiveUser();
    }


    public LiveData<List<User>> getAllLiveUsers() {
        return localDb.getUserRepo().getAllLiveUsers();
    }


    void setAllUsers(List<User> allUsers) {
        localDb.getUserRepo().setAllUsers(allUsers);
    }

    public List<MessagePreview> transformToMsgPreviews(List<Message> listMessages) {

        Log.d(TAG, "transformToMsgPreviews: ");
        List<MessagePreview> listPreview = new ArrayList<>();

        for (Message msg : listMessages) {
            if (msg != null) {
                listPreview.add(transformToMsgPreview(msg));
            } else
                Log.e(TAG, "transformToMsgPreviews: userMsg is NULL");
        }

        return listPreview;
    }

    private MessagePreview transformToMsgPreview(Message msg) {

        User recipientUser = localDb.getUserRepo().getCachedUserById(msg.recipientId);
        if (recipientUser == null)
            return null;

        Log.d(TAG, "transformToMsgPreview: recUserId: " + recipientUser.getId() + " _lastMessage: " + msg.text
                + " _date: " + msg.getStringDate());
        return new MessagePreview.MessagePreviewBuilder()
                .withId(recipientUser.getId())
                .withContactName(recipientUser.firstName)
                .withLastMessage(msg.text)
                .withIsPinned(false)
                .withIsReaded(true)
                .withDate(msg.getRealDate())
                .withImageResId(R.drawable.kochek_withback)
                .withMessageType(msg.messageType)
                .buildMessagePreview();
    }

    void cacheUser(User user) {
        localDb.getUserRepo().setCurrUser(user);
    }

    User getCacheUser() {
        return localDb.getUserRepo().getCurrUser();
    }

    public LiveData<List<Message>> getRecentMessageByChat() {
        return localDb.getMessageRepo().getRecentMessageByChat();
    }

    @SuppressLint("CheckResult")
    void loadPhoneContacts() {
        Log.d(TAG, "loadPhoneContacts: ");

        Disposable disposable = localDb.getUserRepo().loadPhoneContacts(getApplication(), true).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();

        compDisposable.add(disposable);
    }

    public void clearChat(long recipientId) {
        Log.d(TAG, "clearChat: with recipientId: " + recipientId);

        Consumer<Long> consumerClean = id -> {
            localDb.getMessageRepo().cleanMessages(id);
        };

        Flowable<Long> flowableClean = Flowable.just(recipientId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());


        Disposable disposable = localDb.getMessageRepo().makeEmptyMessageInsertion(recipientId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnComplete(() -> compDisposable.add(flowableClean
                        .subscribe(consumerClean, Throwable::printStackTrace)))
                .subscribe();

        compDisposable.add(disposable);
    }

    @SuppressLint("CheckResult")
    public void deleteChat(Long recipientId) {
        Log.d(TAG, "deleteChat: with recipientId" + recipientId);
        Consumer<Long> consumer = id -> localDb.getMessageRepo().deleteMessageByRecipientId(id);
        Disposable disposable = Flowable.just(recipientId)
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, Throwable::printStackTrace);

        compDisposable.add(disposable);
    }

    public void clearDisposables() {
        if (compDisposable != null && compDisposable.isDisposed())
            compDisposable.clear();
    }
}
