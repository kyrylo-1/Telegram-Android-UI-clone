package com.shorka.telegramclone_ui.chats_previews_screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.UserMsgs;
import com.shorka.telegramclone_ui.db.UserRepository;
import com.shorka.telegramclone_ui.entities.MessagePreviewEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/16/2018.
 */
public class ChatPreviewViewModel extends AndroidViewModel {

    private UserRepository userRepo;
    private LiveData<User> currUser;
    private LiveData<List<UserMsgs>> allUserMsgs;

    public ChatPreviewViewModel(@NonNull Application application) {
        super(application);

        userRepo = new UserRepository(application);
        currUser = userRepo.getCurrUser();
        allUserMsgs = userRepo.getAllUserMsgs();
    }

    public LiveData<User> getCurrUser() {
        return currUser;
    }

    public LiveData<List<UserMsgs>> getAllUserMsgs() {
        return allUserMsgs;
    }

    public List<MessagePreviewEntity> transformToMsgPreviews(List<UserMsgs> userMsgsList) {

        List<MessagePreviewEntity> listPreview = new ArrayList<>();
        for (UserMsgs userMsg : userMsgsList) {
            listPreview.add(transformToMsgPreview(userMsg));
        }

        return listPreview;
    }

    private MessagePreviewEntity transformToMsgPreview(UserMsgs userMsgs) {

        MessagePreviewEntity entity1 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("BOBBY")
                .withLastMessage(userMsgs.lastMessage)
                .withIsPinned(false)
                .withIsReaded(true)
                .withDate("12:41")
                .withImageResId(R.drawable.kochek_withback)
                .buildMesagePreview();

        return entity1;
    }
}
