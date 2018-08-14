package com.shorka.telegramclone_ui.settings_screen;

import com.shorka.telegramclone_ui.db.User;

/**
 * Created by Kyrylo Avramenko on 8/13/2018.
 */
public interface SettingsContract {

    interface View{
        void showUserInfo(User user);
    }

    interface UserActionsListener {

        void loadUserInfo();

        void loadMessagingSettings();

        void loadGeneralSettings();
    }

}
