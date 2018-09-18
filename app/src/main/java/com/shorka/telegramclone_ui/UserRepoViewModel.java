package com.shorka.telegramclone_ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserRepository;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public abstract class UserRepoViewModel extends AndroidViewModel {

    protected final UserRepository userRepo;

    protected UserRepoViewModel(@NonNull Application application, @NonNull UserRepository userRepo) {
        super(application);
        this.userRepo = userRepo;
    }

    public void cacheUser(User user) {
        userRepo.setCurrUser(user);
    }

    public LiveData<User> getLiveCurrUser() {
        return userRepo.getCurrLiveUser();
    }

    @SuppressLint("CheckResult")
    protected void updateUser(User user){
        Consumer<User> consumer = userRepo::updateUser;
        Flowable.just(user)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(consumer, Throwable::printStackTrace);
    }
}
