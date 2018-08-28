package com.shorka.telegramclone_ui.recycle_views;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class ViewHolderToggle extends BasicViewHolder {

    private TextView title;
    private SwitchCompat toggle;

    public ViewHolderToggle(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.txt_description);
        toggle = itemView.findViewById(R.id.simpleSwitch);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public SwitchCompat getToggle() {
        return toggle;
    }

    public void setToggle(SwitchCompat toggle) {
        this.toggle = toggle;
    }
}
