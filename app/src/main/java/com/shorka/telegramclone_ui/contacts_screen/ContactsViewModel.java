package com.shorka.telegramclone_ui.contacts_screen;

import android.app.Application;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.UserRepoViewModel;
import com.shorka.telegramclone_ui.db.PhoneContact;
import com.shorka.telegramclone_ui.db.UserRepository;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/23/2018.
 */
public class ContactsViewModel extends UserRepoViewModel {

    private static final String TAG = "ContactsViewModel";


    public ContactsViewModel(@NonNull Application application, @NonNull UserRepository userRepo) {
        super(application, userRepo);
    }

    List<PhoneContact> getPhoneContacts(){
        return userRepo.getCachedPhoneContacts();
    }
}
