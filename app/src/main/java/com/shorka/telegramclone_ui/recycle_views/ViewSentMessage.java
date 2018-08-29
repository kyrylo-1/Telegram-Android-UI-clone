package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Converters;

import java.util.Date;


/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class ViewSentMessage extends RecyclerView.ViewHolder implements MessageBindable {

    private static final String TAG = "ViewSentMessage";
    private TextView txtMessageBody, txtTime;

    public ViewSentMessage(View itemView) {
        super(itemView);

//        Log.d(TAG, "ViewSentMessage: constructor init");

        txtMessageBody = itemView.findViewById(R.id.text_sent_message_body);
        txtTime = itemView.findViewById(R.id.text_sent_message_time);
    }

    public void bind(String messageText, Date realDate) {

        Log.d(TAG, "bind:");
        txtMessageBody.setText(messageText);
        txtTime.setText(Converters.dateToHourAndMinute(realDate));
//        Log.d(TAG, "bind: date: " + date + "\nLong: " + Converters.dateToHourAndMinute(date));
    }

}
