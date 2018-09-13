package com.shorka.telegramclone_ui.db;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.MessagePreview;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

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

    public Flowable<Long> deleteMessageById(long messageId){
        return Flowable.fromCallable(() -> {
            messageDao.deleteNonEmptyById(messageId);
            return messageId;
        });
    }

    public Flowable<Long> deleteEmptyMessages (long recipientId){
        return Flowable.fromCallable(() -> {
            messageDao.deleteAllMessages(recipientId, Message.MessageType.EMPTY);
            return recipientId;
        });
    }

    public void deleteMessageByRecipientId(long recipientId){
        messageDao.deleteByRecipientId(recipientId);
    }
}
