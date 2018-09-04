package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kyrylo Avramenko on 8/30/2018.
 */
public class ConversationItem extends ConstraintLayout {

    private static final String TAG = "ConversationItem";

    private TextView txtBody;
    private TextView txtTime;
    private ImageView imgArrow;

    public TextView getTxtBody() {
        return txtBody;
    }

    public TextView getTxtTime() {
        return txtTime;
    }

    public ConversationItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        txtBody = findViewById(R.id.text_message_body);
        txtTime = findViewById(R.id.text_message_time);
        imgArrow = findViewById(R.id.message_arrow);
    }



    public void selectView(boolean doSelect) {
        Log.d(TAG, "selectView: Make makeSelection? " + txtBody.getText() + " __" + doSelect);
        setSelected(doSelect);
        txtBody.setSelected(doSelect);
        imgArrow.setSelected(doSelect);
    }
}
