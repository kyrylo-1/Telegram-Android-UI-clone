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
import com.shorka.telegramclone_ui.recycle_views.ViewSentMessage;

import java.util.List;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MessageListAdapter";
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
        return new ViewSentMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = items.get(position);

        if (message != null)
            ((ViewSentMessage) holder).bind(message.text, message.date);

        else
            Log.e(TAG, "onBindViewHolder: dont find item on position: " + position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {

        //Because it's only demo now
        return VIEW_TYPE_MESSAGE_SENT;
    }
}
