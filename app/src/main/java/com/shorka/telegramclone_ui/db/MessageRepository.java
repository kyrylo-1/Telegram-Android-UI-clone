package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public class MessageRepository extends BaseRepository {

    private static final String TAG = "MessageRepository";
    private final MessageDao messageDao;

    public MessageRepository(Application application) {
        super(application);
        messageDao = appDB.messageDao();
    }

    public LiveData<List<Message>> getRecentMessageByChat() {
        return messageDao.getMostRecentDateAndGrouById();
    }

    public LiveData<List<Message>> getMessagesyRecipientId(long recipientId) {
        return messageDao.getByRecipientId(recipientId);
    }

    public LiveData<Message> getMessageById(long messageId) {
        return messageDao.getMessageById(messageId);
    }


    public void insertMessage(@NonNull Message message) {
        Log.d(TAG, "insertMessage: " + message.text + " id: " + message.getIdMessage());
        messageDao.insert(message);
    }

    public void deleteMessage(@NonNull final Message... message) {
        messageDao.delete(message);
    }

    public Flowable<Long> deleteMessageById(long messageId) {
        return Flowable.fromCallable(() -> {
            Log.d(TAG, "deleteMessageById: " + messageId);
            messageDao.deleteById(messageId);
            return messageId;
        });
    }

    public void cleanMessages(long recipientId) {
        messageDao.cleanByRecipientId(recipientId, Message.MessageType.EMPTY);
    }

    public void deleteMessageByRecipientId(long recipientId) {
        messageDao.deleteByRecipientId(recipientId);
    }

    public Flowable<Long> makeEmptyMessageInsertion(long recipientId) {
        Message m = MessageHelper.createEmptyMessageInstance(recipientId);

        return Flowable.fromCallable(() -> {
            messageDao.insert(m);
            return recipientId;
        }).observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
    }
}
