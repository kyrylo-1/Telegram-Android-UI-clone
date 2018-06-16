package com.shorka.telegramclone_ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private List<Object> items = new ArrayList<>();

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

    private void configureViewSwitch(ViewHolderToggle vh1, int position) {

        SettingsTextEntity entity = (SettingsTextEntity) items.get(position);
        if(entity != null){
            vh1.getTitle().setText(entity.getMainText());
            vh1.getToggle().setChecked(true);
        }
    }

    private void configureViewTextWithOption(ViewHolderTextWithOption vh1, int position) {

        SettingsTextEntity entity = (SettingsTextEntity) items.get(position);
        if(entity != null){
            vh1.getTitle().setText(entity.getMainText());
            vh1.getOption().setText(entity.getSecondText());
        }
    }

    private void configureSubTitle(ViewHolderSubTitle vh, int position) {

        SettingsTextEntity settingsTextEntity = (SettingsTextEntity) items.get(position);

        vh.getTitle().setText(settingsTextEntity.getMainText());
        vh.getSubTitle().setText(settingsTextEntity.getSecondText());
    }

    private void configurePlainText(ViewPlainText vh, int position) {

        SettingsTextEntity settingsTextEntity = (SettingsTextEntity) items.get(position);
        vh.getTitle().setText(settingsTextEntity.getMainText());
    }
}
