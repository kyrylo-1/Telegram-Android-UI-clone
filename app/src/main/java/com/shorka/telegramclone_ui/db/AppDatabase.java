package com.shorka.telegramclone_ui.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "user_database";

    public abstract UserDao userDao();

//    public abstract UserMessagesDao userMessagesDao();

    public static AppDatabase getDatabase(final Context context, boolean memoryOnly) {
        Log.d(TAG, "getDatabase: ");
        if (INSTANCE != null) {
            return INSTANCE;
        }

        Log.d(TAG, "getDatabase: INSTANCE == null");

        RoomDatabase.Builder<AppDatabase> appDB;
        if (memoryOnly) {
            appDB = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME);
        } else {
            appDB = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                    AppDatabase.class);
        }

        INSTANCE = appDB.addCallback(sRoomDatabaseCallback)
                .fallbackToDestructiveMigration()
                .build();

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Log.d(TAG, "onCreate: populate database");
                    new PopulateDbAsync(INSTANCE).execute();
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    Log.d(TAG, "onOpen: ");
                    super.onOpen(db);
                }


            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;
//        private final UserMessagesDao mUserMessagesDao;

        PopulateDbAsync(AppDatabase db) {
            Log.d(TAG, "PopulateDbAsync: ");
            mDao = db.userDao();
//            mUserMessagesDao = db.userMessagesDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll();

            mDao.insert(createUserEntity(1, "Kyrylo", "I am android dev", "204-234- 6712",
                    "mirecsy23"));
//            mDao.insert(createUserEntity(2, "Bob", "", "204-234- 6712"));
//            mDao.insert(createUserEntity(4, "Alex", "hahaha", "234-214- 6715"));
//            mDao.insert(createUserEntity(5, "Ivan", "I live in NYC.", "904-124- 6413"));
//            mDao.insert(createUserEntity(6, "Pavel Durov", null, "204-224- 3242"));
//            mDao.insert(createUserEntity(7, "Lisa S", null, "204-224- 3242"));
//            mDao.insert(createUserEntity(8, "Mr. Heisenberg", null, "204-224-0000"));
//            mDao.insert(createUserEntity(9, "Jack Uni", null, "204-224- 3342"));
//            mDao.insert(createUserEntity(10, "Anna Smith", null, "204-224- 6542"));
//
//            long userId = 1;
//            mUserMessagesDao.insertUserMessages(createUserMessages(2, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(4, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(5, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(6, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(7, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(8, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(9, userId,
//                    "Are you even lifting, bro?"));
//            mUserMessagesDao.insertUserMessages(createUserMessages(10, userId,
//                    "Are you even lifting, bro?"));

            return null;
        }

        private User createUserEntity(long id, String name, @Nullable String bio, String phoneNumber
                , String username) {
            User user = new User(id);
            user.name = name;
            user.bio = bio;
            user.phoneNumber = phoneNumber;
            user.username = username;
            return user;
        }

        private UserMessages createUserMessages(long recipientId, long userId, String lastMessage) {
            UserMessages userMessages = new UserMessages(recipientId, userId);
            userMessages.lastMessage = lastMessage;
            return userMessages;
        }


    }

}
