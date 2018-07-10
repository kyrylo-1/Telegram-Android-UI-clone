package com.shorka.telegramclone_ui;

import android.app.Application;

/**
 * Created by Kyrylo Avramenko on 6/27/2018.
 */
public class App extends Application {

    public static App instance;
//    private AppDatabase database;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        database = Room.databaseBuilder(this,AppDatabase.class, "database")
//                .build();
    }

    public static App getInstance() {
        return instance;
    }

//    public AppDatabase getDatabase() {
//        return database;
//    }
}
