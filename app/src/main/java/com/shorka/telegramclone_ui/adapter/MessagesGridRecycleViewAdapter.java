package com.shorka.telegramclone_ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.entities.MessagePreview;
import com.shorka.telegramclone_ui.recycle_views.ViewMessageGridPreview;
import com.shorka.telegramclone_ui.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 6/19/2018.
 */
public class MessagesGridRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessagePreview> items = new ArrayList<>();
    private Context context;
    private static final String TAG = "MessagesGridRecycleView";


    public MessagesGridRecycleViewAdapter(Context context) {
        this.context = context;
    }


    public void setItems(List<MessagePreview> items) {
        this.items = items;
        Log.d(TAG, "setItemsMessages with size: " + items.size());
        notifyDataSetChanged();
    }

    public MessagePreview getMessagePreview(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewInflated = inflater.inflate(R.layout.layout_message_preview, parent, false);
        viewHolder = new ViewMessageGridPreview(viewInflated);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        ViewMessageGridPreview viewMessageGridPreview = (ViewMessageGridPreview) viewHolder;
        MessagePreview entity = items.get(position);
        if (entity != null) {
//            viewMessageGridPreview.s
            viewMessageGridPreview.getTitle().setText(entity.getContactName());
            viewMessageGridPreview.getMessageContent().setText(entity.getLastMessage());
            viewMessageGridPreview.setPinImage(entity.isPinned());
            viewMessageGridPreview.setMessageSentTime(DateHelper.getProperDateFormat(entity.getDate()));
//            Log.d(TAG, "onBindViewHolder: entity.getImageResId()" + entity.getImageResId());
            viewMessageGridPreview.setImage(ContextCompat.getDrawable(context, entity.getImageResId()));
        } else {
            Log.e(TAG, "onBindViewHolder: entity is NULL at position: " + position);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
