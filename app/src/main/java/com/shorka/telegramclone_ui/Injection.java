package com.shorka.telegramclone_ui;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.AppDatabase;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/2/2018.
 */
public class Injection {

    private static UserRepository userRepo;

    public static UserRepository provideUserRepo(@NonNull Application application) {

        if (userRepo == null)
            userRepo = new UserRepository(application);

        return userRepo;
    }

}
