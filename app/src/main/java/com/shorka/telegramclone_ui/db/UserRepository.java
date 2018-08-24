package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.shorka.telegramclone_ui.ContactsFetcher;
import com.shorka.telegramclone_ui.chats_previews_screen.ChatPreviewViewModel;
import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
public class UserRepository {

    private static final String TAG = "UserRepository";

    private final UserDao userDao;
    private final MessageDao messageDao;
    private List<User> allUsers;
    private User currUser;
    private final AppDatabase appDB;
    private List<MessagePreview> cachedMessagePreviews;
    private List<PhoneContact> cachedPhoneContacts;
    private Context context;

    public UserRepository(Application application) {
        Log.d(TAG, "UserRepository: constructor");
        context = application;
        appDB = AppDatabase.getInstance(application, true);
        userDao = appDB.userDao();
        messageDao = appDB.messageDao();
    }

    //<editor-fold desc="User related methods">
    public LiveData<User> getCurrLiveUser() {
        return userDao.getById(1);
    }

    public LiveData<List<Message>> getAllLiveMessages() {
        return appDB.messageDao().getAll();
    }

    public LiveData<List<User>> getAllLiveUsers() {
        return userDao.getAll();
    }

    public void updateUser(User user) {

        if (user != null)
            userDao.update(user);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    //TODO: optimize for search

    public User getCachedUserById(long id) {

        for (User user : allUsers) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        Log.d(TAG, "setCurrUser: ");
        this.currUser = currUser;
    }
    //</editor-fold>


    //<editor-fold desc="Messages related methods">
    public LiveData<List<Message>> getRecentMessageByChat() {
        return messageDao.getMostRecentDateAndGrouById();
    }

    public List<MessagePreview> getCachedMessagePreviews() {
        return cachedMessagePreviews;
    }

    public void setCachedMessagePreviews(List<MessagePreview> cachedMessagePreviews) {
        this.cachedMessagePreviews = cachedMessagePreviews;
    }

    public LiveData<List<Message>> getMessagesyRecipientId(long id) {
        return messageDao.getByRecipientId(id);
    }

    public void insertMessage(Message message) {
        Log.d(TAG, "insertMessage: ");
        if (message != null)
            messageDao.insert(message);
    }
    //</editor-fold>

    public LiveData<List<PhoneContact>> getLivePhoneContacts() {
        return userDao.getPhoneContacts();
    }

    public Flowable<List<PhoneContact>> loadPhoneContacts(@NonNull Context context, boolean doRefresh) {

        if (cachedPhoneContacts != null && !doRefresh)
            return Flowable.just(cachedPhoneContacts);

        return Flowable.fromCallable(() -> {
            Log.d(TAG, "RXjava call method to fetch contacts: ");
            List<PhoneContact> list = ContactsFetcher.fetch(context);
            cachedPhoneContacts = list;
            return list;
        });
    }


    public List<PhoneContact> getCachedPhoneContacts() {
        return cachedPhoneContacts;
    }

    public void setCachedPhoneContacts(List<PhoneContact> cachedPhoneContacts) {
        this.cachedPhoneContacts = cachedPhoneContacts;
    }
}
