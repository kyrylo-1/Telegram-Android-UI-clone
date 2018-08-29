package com.shorka.telegramclone_ui.utils;

import android.support.annotation.IntDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;

import com.shorka.telegramclone_ui.RollingFabState;

/**
 * Created by Kyrylo Avramenko on 7/13/2018.
 */
public class FabHelper {

    private static final String TAG = "FabHelper";
    private int mRollingState = RollingFabState.IDLE;
    private final Interpolator LINEAR_INTERPOLATOR;
    private final int mOffscreenTranslation;
    private final long duration;

    public int getOffscreenTranslation() {
        return mOffscreenTranslation;
    }
    public int getRollingState() {
        return mRollingState;
    }


    public FabHelper(View fab, long duration) {
        LINEAR_INTERPOLATOR = new LinearInterpolator();
        mOffscreenTranslation = fab.getHeight() + fab.getHeight() / 2;
//        Log.d(TAG,  " fab.getHeight(): " +fab.getHeight() + "\n mOffscreenTranslation:" + mOffscreenTranslation);
        this.duration = duration;
    }


    private ViewPropertyAnimatorListener mRollingOutListener = new ViewPropertyAnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(View view) {
            mRollingState = RollingFabState.ROLLING_OUT;
        }

        @Override
        public void onAnimationEnd(View view) {
            mRollingState = RollingFabState.ROLLED_OUT;
        }
    };

    private ViewPropertyAnimatorListener mRollingInListener = new ViewPropertyAnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(View view) {
            mRollingState = RollingFabState.ROLLING_IN;
        }

        @Override
        public void onAnimationEnd(View view) {
            mRollingState = RollingFabState.IDLE;
        }
    };

    public void postRollFabOutCompletely(final View fab) {
//        Log.d(TAG, "postRollFabOutCompletely: ");
        ViewCompat.postOnAnimation(fab, new Runnable() {
            @Override
            public void run() {
//                Log.d(TAG, "run: postRollFabOutCompletely. mOffscreenTranslation: " + mOffscreenTranslation);
                ViewCompat.animate(fab)
                        .translationY(mOffscreenTranslation)
                        .setDuration(duration)
                        .setInterpolator(LINEAR_INTERPOLATOR)
                        .setListener(mRollingOutListener)
                        .start();
            }
        });
    }

    public void postRollFabInCompletely(final View fab) {
//        Log.d(TAG, "postRollFabInCompletely: ");
        ViewCompat.postOnAnimation(fab, new Runnable() {
            @Override
            public void run() {
//                Log.d(TAG, "run: postRollFabInCompletely");
                ViewCompat.animate(fab).translationY(0f).
                        setDuration(duration)
                        .setInterpolator(LINEAR_INTERPOLATOR)
                        .setListener(mRollingInListener)
                        .start();
            }
        });
    }
}
