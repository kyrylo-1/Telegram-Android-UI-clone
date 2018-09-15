package com.shorka.telegramclone_ui.db;

import android.annotation.SuppressLint;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.DefaultDataGenerator;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
@Database(entities = {User.class, Message.class, PhoneContact.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "user_database";

    public abstract UserDao userDao();

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

                @SuppressLint({"RxSubscribeOnError", "RxLeakedSubscription"})
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    Log.d(TAG, "onCreate: populate database");
                    populateDb(INSTANCE).subscribe();
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    Log.d(TAG, "onOpen: DB");
                    super.onOpen(db);
                }
            };


    @SuppressLint("CheckResult")
    private static Completable populateDb(@NonNull AppDatabase db) {
        return Completable.fromAction(() -> {
            UserDao userDao = db.userDao();
            MessageDao messageDao = db.messageDao();
            for (User user : DefaultDataGenerator.generateUsers()) {
                userDao.insert(user);
            }

            List<Message> messages = DefaultDataGenerator.generateMessages();

            for (Message message : messages) {
                messageDao.insert(message);
            }

        }).observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
    }
}
