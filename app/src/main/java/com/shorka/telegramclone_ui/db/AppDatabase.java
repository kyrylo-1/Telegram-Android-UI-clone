package com.shorka.telegramclone_ui.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.DefaultDataGenerator;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
@Database(entities = {User.class, UserMessages.class, Message.class, PhoneContact.class}, version = 6, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "user_database";

    public abstract UserDao userDao();
    public abstract UserMsgDao userMsgDao();
    public abstract MessageDao messageDao();

    public static AppDatabase getInstance(final Context context, final boolean inMemory) {
        Log.d(TAG, "getInstance: ");

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "getInstance: INSTANCE == null");
                    INSTANCE = buildDatabase(context.getApplicationContext(), inMemory);
                }
            }
        }
        return INSTANCE;
    }


    private static AppDatabase buildDatabase(final Context context, final boolean inMemory) {
        RoomDatabase.Builder<AppDatabase> appDB;
        //for testing
        if (inMemory)
            appDB = Room.inMemoryDatabaseBuilder(context, AppDatabase.class);

        else {
            appDB = Room.databaseBuilder(context, AppDatabase.class,
                    DB_NAME);
        }

        return appDB.addCallback(sRoomDatabaseCallback)
                .fallbackToDestructiveMigration()
                .build();
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
                    Log.d(TAG, "onOpen: DB");
                    super.onOpen(db);
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao userDao;
        private final MessageDao messageDao;

        PopulateDbAsync(@NonNull AppDatabase db) {
            Log.d(TAG, "PopulateDbAsync: ");
            userDao = db.userDao();
            messageDao = db.messageDao();
        }

//        @Override
//        protected Void doInBackground(final Void... params) {
//
//            Log.d(TAG, "doInBackground: actually insert data of users");
//
//            for (User user : DefaultDataGenerator.generateUsers()) {
//                Log.d(TAG, "doInBackground: insert user with id: " + user.getIdMessage());
//                userDao.insert(user);
//            }
//
//            for (UserMessages userMessages : DefaultDataGenerator.generateLastUserMessages()) {
//                Log.d(TAG, "doInBackground: insert userMessages with id:" + userMessages.getRecipientId());
//                userMsgsDao.insertUserMessages(userMessages);
//            }
//
//            return null;
//        }
//    }

        @Override
        protected Void doInBackground(final Void... params) {

            Log.d(TAG, "doInBackground: actually insert data of users");

            for (User user : DefaultDataGenerator.generateUsers()) {
                Log.d(TAG, "doInBackground: insert user with id: " + user.getId());
                userDao.insert(user);
            }

//            for (UserMessages userMessages : DefaultDataGenerator.generateLastUserMessages()) {
//                Log.d(TAG, "doInBackground: insert userMessages with id:" + userMessages.getRecipientId());
//                userMsgsDao.insertUserMessages(userMessages);
//            }

            for (Message message : DefaultDataGenerator.generateMessages()) {
//                Log.d(TAG, "doInBackground: insert userMessages with id:" + message.recipientId);
                messageDao.insert(message);
            }

            return null;
        }
    }
}
