package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
        new updateUserAsyncTask(userDao).execute(user);
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
        new insertMessageAsyncTask(messageDao).execute(message);
    }

    //</editor-fold>

    public LiveData<List<PhoneContact>> getLivePhoneContacts() {
        return userDao.getPhoneContacts();
    }

    private FetchContactsAsync fetchContactsAsync;

    public void loadContacts() {

        if (fetchContactsAsync == null)
            fetchContactsAsync = new FetchContactsAsync(context);

        fetchContactsAsync.execute();
    }

    public void cancelLoadContacts() {
        if (!fetchContactsAsync.isCancelled())
            fetchContactsAsync.cancel(true);
    }

    public List<PhoneContact> getCachedPhoneContacts() {
        return cachedPhoneContacts;
    }


    private static class insertMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao messageDao;

        insertMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            messageDao.insert(params[0]);
            return null;
        }
    }

    private static class updateUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao dao;

        updateUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            dao.update(params[0]);
            return null;
        }
    }

    //TODO: still can cause leaks, becase class is non-static. Need to fix it
    private class FetchContactsAsync extends AsyncTask<Void, Void, ArrayList<PhoneContact>> {

        private final String DISPLAY_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;

        private final String FILTER = DISPLAY_NAME + " NOT LIKE '%@%'";
        private final String ORDER = String.format("%1$s COLLATE NOCASE", DISPLAY_NAME);

        private final String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        private WeakReference<Context> contextRef;
        public FetchContactsAsync(Context context) {
            contextRef = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<PhoneContact> doInBackground(Void... voids) {
            try {
                ArrayList<PhoneContact> contacts = new ArrayList<>();

                ContentResolver cr = contextRef.get().getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, FILTER, null, ORDER);
                if (cursor != null && cursor.moveToFirst()) {

                    do {
                        // get the contact's information
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        Integer hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        // get the user's phone number
                        String phone = null;
                        if (hasPhone > 0) {
                            Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            if (cp != null && cp.moveToFirst()) {
                                phone = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                cp.close();
                            }
                        }

                        if (!TextUtils.isEmpty(phone)) {
                            contacts.add(new PhoneContact(0, phone, name));
                        }

                    } while (cursor.moveToNext());

                    // clean up cursor
                    cursor.close();
                }

                return contacts;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PhoneContact> list) {
            if (list != null) {
                cachedPhoneContacts = list;
                Log.d(TAG, "onPostExecute: Success");
            } else {
                Log.e(TAG, "onPostExecute: FAILURE. phoneContacts IS NULL");
            }
        }
    }
}
