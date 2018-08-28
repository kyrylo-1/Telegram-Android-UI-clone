package com.shorka.telegramclone_ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.entities.SettingsTextEntity;
import com.shorka.telegramclone_ui.recycle_views.ViewHolderSubTitle;
import com.shorka.telegramclone_ui.recycle_views.ViewHolderTextWithOption;
import com.shorka.telegramclone_ui.recycle_views.ViewHolderToggle;
import com.shorka.telegramclone_ui.recycle_views.ViewPlainText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int PLAIN = 0, SUBTITLE = 1, OPTION = 2, TOGGLE = 3;
    private List<Object> items;

    public ComplexRecyclerViewAdapter(ArrayList<Object> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case SUBTITLE:
                View v1 = inflater.inflate(R.layout.layout_viewholder_title_subtitle, parent, false);
                viewHolder = new ViewHolderSubTitle(v1);
                break;

            case OPTION:
                View v2 = inflater.inflate(R.layout.layout_viewholder_option, parent, false);
                viewHolder = new ViewHolderTextWithOption(v2);
                break;

            case TOGGLE:
                View v3 = inflater.inflate(R.layout.layout_viewholder_text_with_switch, parent, false);
                viewHolder = new ViewHolderToggle(v3);
                break;

            default:
                View vDef = inflater.inflate(R.layout.layout_plain_text, parent, false);
                viewHolder = new ViewPlainText(vDef);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case SUBTITLE:
                ViewHolderSubTitle v1 = (ViewHolderSubTitle) viewHolder;
                configureSubTitle(v1, position);
                break;

            case OPTION:
                ViewHolderTextWithOption vh1 = (ViewHolderTextWithOption) viewHolder;

                configureViewTextWithOption(vh1, position);
                break;
            case TOGGLE:
                ViewHolderToggle vh2 = (ViewHolderToggle) viewHolder;
                configureViewSwitch(vh2, position);
                break;
            default:
                ViewPlainText vhDef = (ViewPlainText) viewHolder;
                configurePlainText(vhDef, position);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {

        SettingsTextEntity entity = (SettingsTextEntity) items.get(position);
        if (entity.getTextType() == SettingsTextEntity.TextType.PlainText) {
            return PLAIN;
        } else if (entity.getTextType() == SettingsTextEntity.TextType.TextWithSelect) {
            return OPTION;
        } else if (entity.getTextType() == SettingsTextEntity.TextType.TextWithToggle) {
            return TOGGLE;
        } else
            return SUBTITLE;
    }

    public void setItems(List<Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private void configureViewSwitch(ViewHolderToggle vh, int position) {

        SettingsTextEntity ste = (SettingsTextEntity) items.get(position);
        if(ste != null){
            vh.getTitle().setText(ste.getMainText());
            vh.getToggle().setChecked(true);
            vh.setIdRes(ste.getIdRes());
        }
    }

    private void configureViewTextWithOption(ViewHolderTextWithOption vh, int position) {

        SettingsTextEntity ste = (SettingsTextEntity) items.get(position);
        if(ste != null){
            vh.getTitle().setText(ste.getMainText());
            vh.getOption().setText(ste.getDescription());
            vh.setIdRes(ste.getIdRes());
        }
    }

    private void configureSubTitle(ViewHolderSubTitle vh, int position) {

        SettingsTextEntity ste = (SettingsTextEntity) items.get(position);

        vh.getTitle().setText(ste.getMainText());
        vh.getSubTitle().setText(ste.getDescription());
        vh.setIdRes(ste.getIdRes());
    }

    private void configurePlainText(ViewPlainText vh, int position) {

        SettingsTextEntity ste = (SettingsTextEntity) items.get(position);
        vh.getTitle().setText(ste.getMainText());
        vh.setIdRes(ste.getIdRes());
    }
}
