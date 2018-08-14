package com.shorka.telegramclone_ui.chats_previews_screen;

import com.shorka.telegramclone_ui.db.User;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
public interface ChatPreviewContract {

    interface View{

        void showChats(List<User> users);

        void showNewMessage();

        void showChatMessages(long idRecipient);

        void updateUserDetail(User user);
    }

    interface UserActionsListener {

        void loadChats();

        void writeNewMessage();

        void openChatMessages(long idRecipient);



    }

}
