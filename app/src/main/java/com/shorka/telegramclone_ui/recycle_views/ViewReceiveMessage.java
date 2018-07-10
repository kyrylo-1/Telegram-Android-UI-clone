package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.models.Message;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class ViewReceiveMessage extends RecyclerView.ViewHolder {

    private TextView mTxtMessageBody, mTxtTime;

    public ViewReceiveMessage(View itemView) {
        super(itemView);

        mTxtMessageBody = (TextView) itemView.findViewById(R.id.text_receive_message_body);
        mTxtTime = (TextView) itemView.findViewById(R.id.text_receive_message_time);
    }

    void bind(Message message){

//        mTxtMessageBody.setText(message.getMessage());
//        mTxtTime.setText((int) message.getCreatedAt());
    }
}
