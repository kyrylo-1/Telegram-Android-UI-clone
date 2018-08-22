package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.util.List;


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

    public UserRepository(Application application) {
        Log.d(TAG, "UserRepository: constructor");
        appDB = AppDatabase.getInstance(application, true);
        userDao = appDB.userDao();
        messageDao = appDB.messageDao();
    }

    //<editor-fold desc="User related methods">
    public LiveData<User> getCurrLiveUser() {
        return userDao.getById(1);
    }

    public LiveData<List<Message>> getAllLiveMessages(){
        return appDB.messageDao().getAll();
    }

    public LiveData<List<User>> getAllLiveUsers() {
        return userDao.getAll();
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
        this.currUser = currUser;
    }
    //</editor-fold>


    public LiveData<List<Message>> getRecentMessageByChat(){
        return messageDao.getMostRecentDateAndGrouById();
    }

    public List<MessagePreview> getCachedMessagePreviews() {
        return cachedMessagePreviews;
    }

    public void setCachedMessagePreviews(List<MessagePreview> cachedMessagePreviews) {
        this.cachedMessagePreviews = cachedMessagePreviews;
    }

    public LiveData<List<Message>> getMessagesyRecipientId(long id){
        return messageDao.getByRecipientId(id);
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
