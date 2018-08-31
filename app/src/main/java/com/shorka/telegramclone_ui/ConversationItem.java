package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kyrylo Avramenko on 8/30/2018.
 */
public class ConversationItem extends ConstraintLayout {

    private static final String TAG = "ConversationItem";
    private final Context context;
    
    private TextView txtBody;
    private TextView txtTime;
    private ConstraintLayout constraintLayout;
    private boolean isSelected;
    public ConversationItem(Context context) {
        super(context);
        this.context = context;
        isSelected =false;
    }

    public ConversationItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "ConversationItem: 2");
        this.context = context;
        isSelected = false;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        txtBody = findViewById(R.id.text_receive_message_body);
        txtTime  = findViewById(R.id.text_receive_message_time);

//        this.setOnClickListener(v -> click());
        this.setOnLongClickListener(v -> longClick());
    }


//    private void click(){
//        Log.d(TAG, "onClick: ");
//    }

    private boolean longClick(){
        Log.d(TAG, "longClick: ");
//        if (txtBody.hasSelection()) {
//
//            Log.d(TAG, "longClick: hasSelection");
//            return false;
//        }
        
        setSelected(true);
        txtBody.setSelected(true);
        performClick();
        return true;
    }

//    public void unSelected(){
//        setSelected(false);
//    }

}
