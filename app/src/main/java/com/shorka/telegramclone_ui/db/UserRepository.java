package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
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
    private LiveData<User> currUser;
    private LiveData<List<UserMsgs>> allUserMsgs;

    public LiveData<List<UserMsgs>> getAllUserMsgs() {
        return allUserMsgs;
    }

    public UserRepository(Application application) {
        Log.d(TAG, "UserRepository: constructor");
        AppDatabase appDB = AppDatabase.getDatabase(application, true);
        userDao = appDB.userDao();
        currUser = userDao.getById(1);
        allUserMsgs = appDB.userMsgDao().getAllUserMessages();
    }

    public LiveData<User> getCurrUser() {
        return currUser;
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        updateAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
