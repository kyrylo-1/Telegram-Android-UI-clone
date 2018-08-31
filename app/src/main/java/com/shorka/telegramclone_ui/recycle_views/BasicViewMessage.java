package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shorka.telegramclone_ui.ClearSelectionCallBack;
import com.shorka.telegramclone_ui.ConversationItem;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Converters;
import com.shorka.telegramclone_ui.db.Message;

import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 8/31/2018.
 */
public class BasicViewMessage extends RecyclerView.ViewHolder {

    private static final String TAG = "BasicViewMessage";
    private ConversationItem convoItem;
    private Message message;
    private long idMessage;

    public BasicViewMessage(View itemView) {
        super(itemView);

//        convoItem = itemView.findViewById(messageType == Message.RECEIVED ? R.id.message_layout : R.id.message_layout_sent);

    }

    public void bind(Message message) {
        this.message = message;
        idMessage = message.getIdMessage();
        if(convoItem != null){
            convoItem.getTxtBody().setText(message.text);
            convoItem.getTxtTime().setText(Converters.dateToHourAndMinute(message.getRealDate()));

        }

        else {
            Log.e(TAG, "bind: convoItem of message: " + message.text + " is NULL");
        }

//        message.clearSelection(() -> Log.d(TAG, "onClear: with id: " + message.getIdMessage()));
    }

    public void selectMessage(boolean doSelect) {
        convoItem.selectView(doSelect);
    }

    public long getIdMessage() {
        return idMessage;
    }

    public void setConvoItem(ConversationItem convoItem) {
        this.convoItem = convoItem;
    }
}
