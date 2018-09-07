package com.shorka.telegramclone_ui.recycle_views;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kyrylo Avramenko on 6/16/2018.
 */
public class ViewMessageGridPreview extends RecyclerView.ViewHolder {

    private static final String TAG = "ViewMessageGridPreview";

    private TextView title, messageContent, messageSentTime, draftLabel;
    private ImageView pinImage;
    private CircleImageView contactImage;

    public ViewMessageGridPreview(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.message_title);
        messageContent = itemView.findViewById(R.id.message_content);
        messageSentTime = itemView.findViewById(R.id.message_sent_time);
        draftLabel = itemView.findViewById(R.id.draft_label);

        pinImage = itemView.findViewById(R.id.pin_image);
        contactImage = itemView.findViewById(R.id.img_chat_contact);
    }

    public TextView getTitle() {
        return title;
    }


    public TextView getMessageContent() {
        return messageContent;
    }

    public void setImage(Drawable drawable) {
        contactImage.setImageDrawable(drawable);
    }


    public void setMessageSentTime(String time) {
        messageSentTime.setText(time);
    }

    public void setPinImage(boolean isPinned) {
        pinImage.setVisibility(isPinned ? View.VISIBLE : View.INVISIBLE);
    }

    public void makeDraftLabelVisible(boolean doMakeVisible) {
        draftLabel.setVisibility(doMakeVisible ? View.VISIBLE : View.GONE);
    }
}
