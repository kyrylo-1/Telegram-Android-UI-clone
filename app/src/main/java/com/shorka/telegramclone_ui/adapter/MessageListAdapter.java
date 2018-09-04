package com.shorka.telegramclone_ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.recycle_views.BasicViewMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kyrylo Avramenko on 6/29/2018.
 */
public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MessageListAdapter";
    private List<Message> itemsMessages;
    private Set<Message> batchSelected = new HashSet<>();

    public MessageListAdapter() {

    }

    public void setItemsMessages(List<Message> itemsMessages) {
        this.itemsMessages = itemsMessages;
        notifyDataSetChanged();
    }

    public void toggleSelection(Message message) {

        if (!batchSelected.remove(message)){
            Log.d(TAG, "toggleSelection: add batchSelected message: " + message.text);
            batchSelected.add(message);
            message.makeSelection();
        }
        else {
            message.clearSelection();
        }

    }

    public void clearSelectedItems() {

        for (Message m : batchSelected) {
                m.clearSelection();
        }

        batchSelected.clear();
        Log.d(TAG, "clearSelectedItems: ");
    }

    public int getSizeOfSelectedItems() {
        return batchSelected == null ? 0: batchSelected.size();
    }

    public Set<Message> getSelectedItems(){
        return batchSelected;
    }

    public Message getItem(int position) {
        return itemsMessages.get(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //get proper id of resource layout
        Log.d(TAG, "onCreateViewHolder: ");
        int res = viewType == Message.RECEIVED ? R.layout.item_message_received : R.layout.item_message_sent;

        View view = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        return new BasicViewMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = itemsMessages.get(position);

        if (message == null) {
            Log.e(TAG, "onBindViewHolder: dont find item on position: " + position);
            return;
        }

        BasicViewMessage bvm = (BasicViewMessage) holder;

        bvm.bind(message);
    }

    @Override
    public int getItemCount() {
        return itemsMessages == null ? 0 : itemsMessages.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message msg = itemsMessages.get(position);

        return msg.messageType;
    }


    //TODO optimize retrieval of BasicMessage item using binary sort. So, list should be sorted in 1st place
//    @Nullable
//    private BasicViewMessage getViewMessageById(long messageId) {
//        for (BasicViewMessage bvm : listViewsMsgs) {
//            if (bvm.getIdMessage() == messageId)
//                return bvm;
//        }
//        return null;
//    }

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
