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
@Database(entities = {User.class, UserMsgs.class}, version = 4, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "user_database";

    public abstract UserDao userDao();

    public abstract UserMsgDao userMsgDao();

    public static AppDatabase getDatabase(final Context context, final boolean inMemory) {
        Log.d(TAG, "getDatabase: ");

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "getDatabase: INSTANCE == null");
                    INSTANCE = buildDatabase(context, inMemory);
                }
            }
        }
        return INSTANCE;
    }


    private static AppDatabase buildDatabase(final Context context, final boolean inMemory) {
        RoomDatabase.Builder<AppDatabase> appDB;
        //for testing
        if (inMemory)
            appDB = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class);

        else {
            appDB = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
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

        private final UserDao dao;
        private final UserMsgDao userMsgsDao;
        PopulateDbAsync(AppDatabase db) {
            Log.d(TAG, "PopulateDbAsync: ");
            dao = db.userDao();
            userMsgsDao = db.userMsgDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            Log.d(TAG, "doInBackground: actually insert data of users");

            for (User user : DefaultDataGenerator.generateUser()) {
                dao.insert(user);
            }

            for (UserMsgs userMsgs : DefaultDataGenerator.generateUserMessages()) {
                userMsgsDao.insertUserMessages(userMsgs);
            }

            return null;
        }
    }
}
