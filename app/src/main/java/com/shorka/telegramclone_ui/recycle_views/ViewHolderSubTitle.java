package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class ViewHolderSubTitle extends RecyclerView.ViewHolder{

    private TextView title, subTitle;

    public ViewHolderSubTitle(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.text_info);
        subTitle = itemView.findViewById(R.id.label_about);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(TextView subTitle) {
        this.subTitle = subTitle;
    }

}
