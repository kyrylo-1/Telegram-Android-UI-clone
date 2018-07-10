package com.shorka.telegramclone_ui.entities;

import android.support.annotation.DrawableRes;
import android.util.Log;

/**
 * Created by Kyrylo Avramenko on 6/19/2018.
 */
public class MessagePreviewEntity {

    private static final String TAG = "MessagePreviewEntity";

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

    public String getDate() {
        return date;
    }

    public int getImageResId() {
        return imageResId;
    }

    private final String id;
    private String contactName;
    private String lastMessage;
    private boolean isPinned;
    private boolean isReaded;
    private String date;
    @DrawableRes
    private int imageResId;

    public MessagePreviewEntity(MessagePreviewBuilder messagePreviewBuilder) {

        this.contactName = messagePreviewBuilder.contactName;
        this.lastMessage = messagePreviewBuilder.lastMessage;
        this.isPinned = messagePreviewBuilder.isPinned;
        this.isReaded = messagePreviewBuilder.isReaded;
        this.date = messagePreviewBuilder.date;
        this.imageResId = messagePreviewBuilder.imageResId;
        id = contactName + Math.random();
    }

    public String getId() {
        return id;
    }


    public static class MessagePreviewBuilder {
        private String contactName;
        private String lastMessage;
        private boolean isPinned;
        private boolean isReaded;
        private String date;
        @DrawableRes
        private int imageResId;



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

        public MessagePreviewBuilder withIsReaded(boolean isReaded) {
            this.isReaded = isReaded;
            return this;
        }

        public MessagePreviewBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public MessagePreviewBuilder withImageResId(@DrawableRes int imageResId) {
            this.imageResId = imageResId;
            return this;
        }

        public MessagePreviewEntity buildMesagePreview() {

            if (isValidateData())
                return new MessagePreviewEntity(this);

            else {
                Log.e(TAG, "buildMesagePreview: Data is NOT validated on build");
                return null;
            }
        }

        private boolean isValidateData() {
            //Do some basic validations to check

            if (contactName.isEmpty()) {
                Log.e(TAG, "isValidateData: contactName.isEmpty");
                return false;
            }

            if (lastMessage.isEmpty()) {
                Log.e(TAG, "isValidateData: lastMessage.isEmpty");
                return false;
            }


            if (date.isEmpty()) {
                Log.e(TAG, "isValidateData: date.isEmpty");
                return false;
            }

            if (imageResId == 0) {
                Log.e(TAG, "isValidateData: imageResId == 0");
                return false;
            }

            return true;
        }
    }

}
