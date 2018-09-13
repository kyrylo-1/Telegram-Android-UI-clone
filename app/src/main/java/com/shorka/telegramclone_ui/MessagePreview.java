package com.shorka.telegramclone_ui;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;

import com.shorka.telegramclone_ui.db.Message;

import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 6/19/2018.
 */
public class MessagePreview {

    private static final String TAG = "MessagePreview";

    public long getRecipientId() {
        return recipientId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public Date getDate() {
        return date;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Message.MessageType
    public int getMessageType() {
        return messageType;
    }

    private long recipientId;
    private String contactName;
    private String lastMessage;
    private boolean isPinned;
    private boolean isReaded;
    private Date date;
    @DrawableRes
    private int imageResId;

    @Message.MessageType
    private int messageType;

    MessagePreview(MessagePreviewBuilder mpb) {

        this.recipientId = mpb.recipient;
        this.contactName = mpb.contactName;
        this.lastMessage = mpb.lastMessage;
        this.isPinned = mpb.isPinned;
        this.isReaded = mpb.isRead;
        this.date = mpb.date;
        this.imageResId = mpb.imageResId;
        this.messageType = mpb.messageType;
    }



    public static class MessagePreviewBuilder {
        private long recipient;
        private String contactName;
        private String lastMessage;
        private boolean isPinned;
        private boolean isRead;
        private Date date;
        @DrawableRes
        private int imageResId;
        @Message.MessageType
        private int messageType;

        public MessagePreviewBuilder withId(long recipient) {
            this.recipient = recipient;
            return this;
        }

        public MessagePreviewBuilder withContactName(String contactName) {
            this.contactName = contactName;
            return this;
        }

        public MessagePreviewBuilder withLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
            return this;
        }

        public MessagePreviewBuilder withIsPinned(boolean isPinned) {
            this.isPinned = isPinned;
            return this;
        }

        public MessagePreviewBuilder withIsReaded(boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        public MessagePreviewBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public MessagePreviewBuilder withMessageType(@Message.MessageType int messageType) {
            this.messageType = messageType;
            return this;
        }

        public MessagePreviewBuilder withImageResId(@DrawableRes int imageResId) {
            this.imageResId = imageResId;
            return this;
        }

        public MessagePreview buildMessagePreview() {

            if (isValidateData())
                return new MessagePreview(this);

            else {
                Log.e(TAG, "buildMessagePreview: Data is NOT validated on build");
                return null;
            }
        }

        private boolean isValidateData() {
            //Do some basic validations to check

            if(messageType == Message.MessageType.EMPTY)
                return true;

            if (TextUtils.isEmpty(contactName)) {
                Log.e(TAG, "isValidateData: contactName.isEmpty");
                return false;
            }

            if (TextUtils.isEmpty(lastMessage)) {
                Log.e(TAG, "isValidateData: lastMessage.isEmpty");
                return false;
            }

            return true;
        }
    }

}
