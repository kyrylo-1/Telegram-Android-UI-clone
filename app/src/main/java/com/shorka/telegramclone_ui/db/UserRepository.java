package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
public class UserRepository {

    private UserDao userDao;
    private static final String TAG = "UserRepository";
    private Single<User> singleCurrUser;
    private User currUser;


    public UserRepository(Application application) {
        AppDatabase appDB = AppDatabase.getDatabase(application, true);
        userDao = appDB.userDao();
    }

    public Single<User> getSingleCurrUser() {
        Log.d(TAG, "getSingleCurrUser: ");
        if (singleCurrUser == null) {
            singleCurrUser = refreshCurrUser();
        }
        return singleCurrUser;
    }

    private Single<User> refreshCurrUser() {
        Log.d(TAG, "refreshCurrUser: ");
        singleCurrUser = userDao.getById(1);
        singleCurrUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    currUser = user;
                });
        return singleCurrUser;
    }

    public void insert(User user) {
        Log.d(TAG, "insert: ");
        userDao.insert(user);
    }

    public void update(User user) {
        Log.d(TAG, "update: ");

        Flowable.just(userDao)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<UserDao>() {
                    @Override
                    public void accept(UserDao userDao) throws Exception {
                        Log.d(TAG, "accept: db update of user");
                        userDao.update(user);
                    }
                });
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }
}
