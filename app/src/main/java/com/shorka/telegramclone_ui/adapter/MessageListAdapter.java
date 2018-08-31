package com.shorka.telegramclone_ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.recycle_views.MessageBindable;
import com.shorka.telegramclone_ui.recycle_views.ViewReceiveMessage;
import com.shorka.telegramclone_ui.recycle_views.ViewSentMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MessageListAdapter";
    private List<Message> items;
    private Set<Message> itemsSelected;

    public MessageListAdapter() {
    }

    public void setItems(List<Message> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void toggleSelection(Message message) {

        if (itemsSelected == null) {
            itemsSelected = new HashSet<>();
        }

        itemsSelected.add(message);
    }

    public void clearSelectedItems() {

        if (itemsSelected == null) return;

        for (Message m: itemsSelected) {

        }

        itemsSelected.clear();
    }

    public Set<Message> getSelectedItems() {
        return itemsSelected;
    }

    public int getSizeOfSelectedItems() {

        return itemsSelected == null ? 0 : itemsSelected.size();
    }

    public Message getItem(int position) {
        return items.get(position);
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

        MessageBindable mb = message.messageType == Message.RECEIVED ?
                (ViewReceiveMessage) holder : (ViewSentMessage) holder;

        mb.bind(message.text, message.getRealDate());
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
