package com.shorka.telegramclone_ui.recycle_views;

import android.util.Log;
import android.view.View;

import com.shorka.telegramclone_ui.ConversationItem;
import com.shorka.telegramclone_ui.R;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class ViewReceiveMessage extends BasicViewMessage {

    private static final String TAG = "ViewReceiveMessage";
    public ViewReceiveMessage(View itemView) {
        super(itemView);

        ConversationItem convoItem = itemView.findViewById(R.id.message_layout);
        if (convoItem == null) {
            Log.e(TAG, "ViewSentMessage: convoItem == null");
        }
        setConvoItem(convoItem);

    }
}
