package com.shorka.telegramclone_ui.contacts_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.db.PhoneContact;
import com.shorka.telegramclone_ui.db.UserRepository;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/23/2018.
 */
public class ContactsViewModel extends AndroidViewModel {

    private static final String TAG = "ContactsViewModel";
    private final UserRepository userRepo;

    public ContactsViewModel(@NonNull Application application, UserRepository userRepo) {
        super(application);
        this.userRepo = userRepo;
    }

    List<PhoneContact> getPhoneContacts(){
//        return userRepo.getP
        return userRepo.getCachedPhoneContacts();
    }
}
