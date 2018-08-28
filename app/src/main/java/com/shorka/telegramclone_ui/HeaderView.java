package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class HeaderView extends LinearLayout {

    private static final String TAG = "HeaderView";

    private TextView txtName;
    private TextView txtLastSeen;
    private CircleImageView imgProfile;

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    public void bindTo(String name, String lastSeen) {
//        Log.d(TAG, "bindTo: firstName: " + name + "\n lastSeen: " + lastSeen);
        this.txtName.setText(name);
        int whiteCol = getResources().getColor(R.color.colorWhite);
        this.txtName.setTextColor(whiteCol);

        this.txtLastSeen.setText(lastSeen);
        this.txtLastSeen.setTextColor(whiteCol);
    }



    public void setTextSize(float size) {
        txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//        lastSeen.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }


    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtViewName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtLastSeen() {
        return txtLastSeen;
    }

    public void setTxtViewLastSeen(TextView txtLastSeen) {
        this.txtLastSeen = txtLastSeen;
    }

    public CircleImageView getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(CircleImageView imgProfile) {
        this.imgProfile = imgProfile;
    }
}
