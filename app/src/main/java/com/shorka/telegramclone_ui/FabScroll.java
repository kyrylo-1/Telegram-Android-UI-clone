package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Kyrylo Avramenko on 7/11/2018.
 */
public class FabScroll {

    private static final String TAG = "FabScroll";
    private final FloatingActionButton fab;
    private final CoordinatorLayout.LayoutParams layoutParams;
    private final LinearInterpolator linearInterpolator;
    private final long DURATION = 50L;

    public FabScroll(FloatingActionButton fab) {
        this.fab = fab;
        layoutParams= (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
        linearInterpolator =new LinearInterpolator();
    }


    public void scroll(int dy){
        if(dy>0){
            int fab_bottomMargin = layoutParams.bottomMargin;
            fab.animate().translationY((float)(fab.getHeight() + fab_bottomMargin)).setInterpolator(linearInterpolator).setDuration(DURATION);
        }
        else {
            fab.animate().translationY(0.0F).setInterpolator(linearInterpolator).setDuration(DURATION);
        }
    }
}
