package com.shorka.telegramclone_ui.recycle_views;

import android.util.Log;
import android.view.View;

import com.shorka.telegramclone_ui.ConversationItem;
import com.shorka.telegramclone_ui.R;

import java.util.Date;


/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class ViewSentMessage extends BasicViewMessage {

    private static final String TAG = "ViewSentMessage";

    public ViewSentMessage(View itemView) {
        super(itemView);
        ConversationItem convoItem = itemView.findViewById(R.id.message_layout_sent);
        if (convoItem == null) {
            Log.e(TAG, "ViewSentMessage: convoItem == null");
        }
        setConvoItem(convoItem);

    }

}
