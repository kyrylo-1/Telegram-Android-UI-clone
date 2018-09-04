package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shorka.telegramclone_ui.ConversationItem;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Converters;
import com.shorka.telegramclone_ui.db.Message;

/**
 * Created by Kyrylo Avramenko on 8/31/2018.
 */
public class BasicViewMessage extends RecyclerView.ViewHolder {

    private static final String TAG = "BasicViewMessage";
    private ConversationItem convoItem;

    private long idMessage;

    public BasicViewMessage(View itemView) {
        super(itemView);
        convoItem = itemView.findViewById(R.id.message_layout);
//        convoItem = itemView.findViewById(messageType == Message.RECEIVED ? R.id.message_layout : R.id.message_layout_sent);

    }

    public void bind(Message message) {
        idMessage = message.getIdMessage();
        if(convoItem != null){
            convoItem.getTxtBody().setText(message.text);
            convoItem.getTxtTime().setText(Converters.dateToHourAndMinute(message.getRealDate()));
        }

        else {
            Log.e(TAG, "bind: convoItem of message: " + message.text + " is NULL");
        }

        message.setSelectionCallback(new Message.SelectionCallBack() {
            @Override
            public void onClear() {
                selectMessage(false);
            }

            @Override
            public void onSelect() {
                selectMessage(true);
            }
        });
    }

    private void selectMessage(boolean isSelected) {
        convoItem.selectView(isSelected);
    }

    public long getIdMessage() {
        return idMessage;
    }

//    public void setConvoItem(ConversationItem convoItem) {
//        this.convoItem = convoItem;
//    }
}
