package com.shorka.telegramclone_ui.settings_screen;

import android.support.annotation.Nullable;

import com.shorka.telegramclone_ui.db.User;

/**
 * Created by Kyrylo Avramenko on 8/14/2018.
 */
public interface ChangeNameContract {

    interface View{
        void returnToPreviousScreen();

        void showName(String firsName, @Nullable String lastName);
    }

    interface UserActionsListener {

        void updateName(@Nullable String firstName, @Nullable String lastName);

        void loadName();

    }
}
