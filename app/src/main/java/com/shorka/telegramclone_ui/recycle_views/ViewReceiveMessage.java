package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Converters;
import com.shorka.telegramclone_ui.db.Message;

import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class ViewReceiveMessage extends RecyclerView.ViewHolder {

    private TextView txtMessageBody, txtTime;

    public ViewReceiveMessage(View itemView) {
        super(itemView);

        txtMessageBody = (TextView) itemView.findViewById(R.id.text_receive_message_body);
        txtTime = (TextView) itemView.findViewById(R.id.text_receive_message_time);
    }

    public void bind(String messageText, Date date){

        txtMessageBody.setText(messageText);
        txtTime.setText(Converters.dateToHourAndMinute(date));
    }
}
