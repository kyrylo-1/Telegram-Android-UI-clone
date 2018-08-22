package com.shorka.telegramclone_ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.recycle_views.ViewReceiveMessage;
import com.shorka.telegramclone_ui.recycle_views.ViewSentMessage;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MessageListAdapter";

    private List<Message> items;

    public MessageListAdapter(Context context) {
    }

    public void setItems(List<Message> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int res = (viewType == Message.RECEIVED) ? R.layout.item_message_received
                : R.layout.item_message_sent;

        View view = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        return (viewType == Message.RECEIVED) ? new ViewReceiveMessage(view) : new ViewSentMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = items.get(position);

        if (message == null) {
            Log.e(TAG, "onBindViewHolder: dont find item on position: " + position);
            return;
        }

        switch (message.messageType) {
            case Message.RECEIVED:
                ((ViewReceiveMessage) holder).bind(message.text, message.date);
                break;

            case Message.SENT:
                ((ViewSentMessage) holder).bind(message.text, message.date);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message msg = items.get(position);

        return msg.messageType;
    }
}
