package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public abstract class BaseRepository  {

    protected final Context context;
    protected final AppDatabase appDB;

    protected BaseRepository(@NonNull Application application) {
        context = application;
        appDB = AppDatabase.getInstance(application, true);
    }
}
