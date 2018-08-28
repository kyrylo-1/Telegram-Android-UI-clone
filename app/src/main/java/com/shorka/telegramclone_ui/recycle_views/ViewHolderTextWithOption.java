package com.shorka.telegramclone_ui.recycle_views;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class ViewHolderTextWithOption extends BasicViewHolder {

    private TextView title, option;


    public ViewHolderTextWithOption(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.txt_description);
        option = itemView.findViewById(R.id.txt_select);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getOption() {
        return option;
    }

    public void setOption(TextView option) {
        this.option = option;
    }

}
