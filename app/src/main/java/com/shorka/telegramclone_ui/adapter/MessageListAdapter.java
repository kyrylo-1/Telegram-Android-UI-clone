package com.shorka.telegramclone_ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.recycle_views.BasicViewMessage;
import com.shorka.telegramclone_ui.recycle_views.ViewReceiveMessage;
import com.shorka.telegramclone_ui.recycle_views.ViewSentMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MessageListAdapter";
    private List<Message> itemsMesssages;
    private final Set<BasicViewMessage> listViewsMsgs;
    private HashMap<Message, BasicViewMessage> itemsSelected;

    public MessageListAdapter() {
        listViewsMsgs = new HashSet<>();
    }

    public void setItemsMesssages(List<Message> itemsMesssages) {
        this.itemsMesssages = itemsMesssages;
        notifyDataSetChanged();
    }

    public void toggleSelection(Message message) {

        if (itemsSelected == null) {
            itemsSelected = new HashMap<>();
        }

        if (itemsSelected.containsKey(message)) {
            itemsSelected.get(message).selectMessage(false);
            itemsSelected.remove(message);
        } else {
            BasicViewMessage bvm = getViewMessageById(message.getIdMessage());
            assert bvm != null;
            bvm.selectMessage(true);
            itemsSelected.put(message, bvm);
        }
    }

    public void clearSelectedItems() {

        Log.d(TAG, "clearSelectedItems: ");
        if (itemsSelected == null || itemsSelected.size() == 0)
            return;

        for (Map.Entry<Message, BasicViewMessage> entry : itemsSelected.entrySet()) {
            entry.getValue().selectMessage(false);
        }

        itemsSelected.clear();
    }

    public Set<Message> getSelectedItems() {
        return itemsSelected.keySet();
    }

    public int getSizeOfSelectedItems() {

        return itemsSelected == null ? 0 : itemsSelected.size();
    }

    public Message getItem(int position) {
        return itemsMesssages.get(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //get proper id of resource layout
        Log.d(TAG, "onCreateViewHolder: ");
        final boolean isReceived = viewType == Message.RECEIVED;
        int res = isReceived ? R.layout.item_message_received
                : R.layout.item_message_sent;

        View view = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
//        return new BasicViewMessage(view);
        return isReceived ? new ViewReceiveMessage(view) : new ViewSentMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = itemsMesssages.get(position);

        if (message == null) {
            Log.e(TAG, "onBindViewHolder: dont find item on position: " + position);
            return;
        }

        BasicViewMessage bvm = message.messageType == Message.RECEIVED ?
                (ViewReceiveMessage) holder : (ViewSentMessage) holder;


        bvm.bind(message);

        if (itemsMesssages.contains(message))
            listViewsMsgs.add(bvm);

        Log.d(TAG, "onBindViewHolder: bind message: " + message.text + " SIZE: " + listViewsMsgs.size());
    }

    @Override
    public int getItemCount() {
        return itemsMesssages == null ? 0 : itemsMesssages.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message msg = itemsMesssages.get(position);

        return msg.messageType;
    }


    //TODO optimize retrieval of BasicMessage item using binary sort. So, list should be sorted in 1st place
    @Nullable
    private BasicViewMessage getViewMessageById(long messageId) {
        for (BasicViewMessage bvm : listViewsMsgs) {
            if (bvm.getIdMessage() == messageId)
                return bvm;
        }
        return null;
    }

//    private class MessageWithView{
//
//        private final Message m;
//        private final BasicViewMessage bvm;
//
//        MessageWithView(Message m, BasicViewMessage bvm){
//            this.m = m;
//            this.bvm = bvm;
//        }
//    }
}
