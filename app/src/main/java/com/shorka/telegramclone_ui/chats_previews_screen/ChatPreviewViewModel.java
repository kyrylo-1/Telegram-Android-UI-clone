package com.shorka.telegramclone_ui.chats_previews_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.PhoneContact;
import com.shorka.telegramclone_ui.db.Message;
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
    private UserRepository userRepo;
    private Context context;
    private CompositeDisposable compDisposable;

    public ChatPreviewViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        context = application;
        this.userRepo = userRepo;
        compDisposable = new CompositeDisposable();
    }

    public LiveData<User> getLiveCurrUser() {
        return userRepo.getCurrLiveUser();
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

    public LiveData<List<Message>> getRecentMessageByChat() {
        return userRepo.getRecentMessageByChat();
    }

    private MessagePreview transformToMsgPreview(Message msg) {

        User user = userRepo.getCachedUserById(msg.recipientId);
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
        return userRepo.getLivePhoneContacts();
    }

    @SuppressLint("CheckResult")
    void loadPhoneContacts() {
        Log.d(TAG, "loadPhoneContacts: ");

        Disposable disposable = userRepo.loadPhoneContacts(context, true).subscribeOn(Schedulers.io())
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
