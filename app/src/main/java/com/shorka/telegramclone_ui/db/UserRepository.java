package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.utils.ContactsFetcher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
public class UserRepository extends BaseRepository {

    private static final String TAG = "UserRepository";

    private final UserDao userDao;
    private List<User> allUsers;
    private User currUser;
    private List<PhoneContact> cachedPhoneContacts;

    public UserRepository(Application application) {
        super(application);

        Log.d(TAG, "UserRepository: constructor");
        userDao = appDB.userDao();
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

    public Flowable<List<PhoneContact>> loadPhoneContacts(@NonNull Context context, boolean doRefresh) {

        if (cachedPhoneContacts != null && !doRefresh)
            return Flowable.just(cachedPhoneContacts);

        return Flowable.fromCallable(() -> {
            Log.d(TAG, "RXjava call method to fetch contacts: ");
            List<PhoneContact> list = ContactsFetcher.fetch(context);
            cachedPhoneContacts = list;
            return list;
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }


    public List<PhoneContact> getCachedPhoneContacts() {
        return cachedPhoneContacts;
    }
}
