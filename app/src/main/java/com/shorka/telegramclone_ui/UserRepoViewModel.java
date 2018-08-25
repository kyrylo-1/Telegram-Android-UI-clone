package com.shorka.telegramclone_ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public abstract class UserRepoViewModel extends AndroidViewModel {

    protected final UserRepository userRepo;

    protected UserRepoViewModel(@NonNull Application application, @NonNull UserRepository userRepo) {
        super(application);
        this.userRepo = userRepo;
    }
}
