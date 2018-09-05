package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public class MessageRepository extends BaseRepository {

    private static final String TAG = "MessageRepository";
    private final MessageDao messageDao;
    private List<MessagePreview> cachedMessagePreviews;

    public MessageRepository(Application application) {
        super(application);
        messageDao = appDB.messageDao();
    }

    public LiveData<List<Message>> getRecentMessageByChat() {
        return messageDao.getMostRecentDateAndGrouById();
    }

    public LiveData<List<Message>> getMessagesyRecipientId(long id) {
        return messageDao.getByRecipientId(id);
    }

    public void insertMessage(@NonNull Message message) {
        messageDao.insert(message);
    }

    public void deleteMessage(@NonNull final Message ...message) {
        messageDao.delete(message);
    }
}
