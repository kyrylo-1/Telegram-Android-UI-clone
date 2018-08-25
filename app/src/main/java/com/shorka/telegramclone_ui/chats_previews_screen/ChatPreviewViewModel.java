package com.shorka.telegramclone_ui.chats_previews_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.LocalDatabase;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.PhoneContact;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;
import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class ChatPreviewViewModel extends AndroidViewModel {

    private static final String TAG = "ChatPreviewViewModel";
    private Context context;
    private final LocalDatabase localDb;
    private CompositeDisposable compDisposable;

    public ChatPreviewViewModel(@NonNull Application application, LocalDatabase localDb) {
        super(application);
        context = application;
        this.localDb = localDb;
        compDisposable = new CompositeDisposable();
    }

    public LiveData<User> getLiveCurrUser() {
        return localDb.getUserRepo().getCurrLiveUser();
    }


    public LiveData<List<User>> getAllLiveUsers() {
        return localDb.getUserRepo().getAllLiveUsers();
    }

    List<User> allUsers() {
        return localDb.getUserRepo().getAllUsers();
    }

    void setAllUsers(List<User> allUsers) {
        localDb.getUserRepo().setAllUsers(allUsers);
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
        localDb.getUserRepo().setCurrUser(user);
    }

    User getCacheUser() {
        return localDb.getUserRepo().getCurrUser();
    }

    public LiveData<List<Message>> getRecentMessageByChat() {
        return localDb.getMessageRepo().getRecentMessageByChat();
    }

    private MessagePreview transformToMsgPreview(Message msg) {

        User user = localDb.getUserRepo().getCachedUserById(msg.recipientId);
        if (user == null)
            return null;

        return new MessagePreview.MessagePreviewBuilder()
                .withId(user.getId())
                .withContactName(user.firstName)
                .withLastMessage(msg.text)
                .withIsPinned(false)
                .withIsReaded(true)
                .withDate("66:66")
                .withImageResId(R.drawable.kochek_withback)
                .buildMesagePreview();
    }

    LiveData<List<PhoneContact>> getLivePhoneContacts() {
        return localDb.getUserRepo().getLivePhoneContacts();
    }

    @SuppressLint("CheckResult")
    void loadPhoneContacts() {
        Log.d(TAG, "loadPhoneContacts: ");

        Disposable disposable = localDb.getUserRepo().loadPhoneContacts(context, true).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();

        compDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
    }

    public void clearDisposables() {
        if (compDisposable != null && compDisposable.isDisposed())
            compDisposable.clear();
    }
}
