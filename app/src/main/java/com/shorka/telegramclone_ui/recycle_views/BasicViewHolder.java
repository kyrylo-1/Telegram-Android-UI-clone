package com.shorka.telegramclone_ui.recycle_views;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kyrylo Avramenko on 8/27/2018.
 */
public class BasicViewHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    protected int idRes = -1;

    public BasicViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        setIdRes(View.generateViewId());
    }

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
        itemView.setId(idRes);
    }

}
