package com.shorka.telegramclone_ui.entities;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Kyrylo Avramenko on 6/19/2018.
 */
public class MessagePreview {

    private static final String TAG = "MessagePreview";

    public long getId() {
        return id;
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

    public String getDate() {
        return date;
    }

    public int getImageResId() {
        return imageResId;
    }

    private long id;
    private String contactName;
    private String lastMessage;
    private boolean isPinned;
    private boolean isReaded;
    private String date;
    @DrawableRes
    private int imageResId;

    MessagePreview(MessagePreviewBuilder messagePreviewBuilder) {

        this.id = messagePreviewBuilder.id;
        this.contactName = messagePreviewBuilder.contactName;
        this.lastMessage = messagePreviewBuilder.lastMessage;
        this.isPinned = messagePreviewBuilder.isPinned;
        this.isReaded = messagePreviewBuilder.isReaded;
        this.date = messagePreviewBuilder.date;
        this.imageResId = messagePreviewBuilder.imageResId;
    }

    public static class MessagePreviewBuilder {
        private long id;
        private String contactName;
        private String lastMessage;
        private boolean isPinned;
        private boolean isReaded;
        private String date;
        @DrawableRes
        private int imageResId;


        public MessagePreviewBuilder withId(long id){
            this.id = id;
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

        public MessagePreview buildMesagePreview() {

            if (isValidateData())
                return new MessagePreview(this);

            else {
                Log.e(TAG, "buildMesagePreview: Data is NOT validated on build");
                return null;
            }
        }

        private boolean isValidateData() {
            //Do some basic validations to check

            if (TextUtils.isEmpty(contactName)) {
                Log.e(TAG, "isValidateData: contactName.isEmpty");
                return false;
            }

            if (TextUtils.isEmpty(lastMessage)) {
                Log.e(TAG, "isValidateData: lastMessage.isEmpty");
                return false;
            }


            if (TextUtils.isEmpty(date)) {
                Log.e(TAG, "isValidateData: date.isEmpty");
                return false;
            }

//            if (imageResId == 0) {
//                Log.e(TAG, "isValidateData: imageResId == 0");
//                return false;
//            }

            return true;
        }
    }

}
