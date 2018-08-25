package com.shorka.telegramclone_ui.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.AppDatabase;
import com.shorka.telegramclone_ui.db.LocalDatabase;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */
public class Injection {

    private static LocalDatabase localDb;

    public static LocalDatabase provideUserRepo(@NonNull Application application) {

        if (localDb == null)
            localDb = new LocalDatabase(application);

        return localDb;
    }

}
