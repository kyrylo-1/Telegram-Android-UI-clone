package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Kyrylo Avramenko on 7/11/2018.
 */
public class FabScrollBehavior extends FloatingActionButton.Behavior {

    private static final String TAG = "FabScrollBehavior";
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final float TENSION_THRESHOLD = 0.5f;
    private static long DURATION = 300L;
    private int mTotalDy;

    private int mRollingState = RollingFabState.IDLE;
    private int mOffscreenTranslation;
    private float mTensionFactor = 0f;

    @IntDef({RollingFabState.IDLE, RollingFabState.ROLLING_OUT, RollingFabState.ROLLING_IN, RollingFabState.ROLLED_OUT})
    private @interface RollingFabState {
        int ROLLING_OUT = -1;
        int ROLLING_IN = 0;
        int ROLLED_OUT = 1;
        int IDLE = 2;
    }

    public FabScrollBehavior() {
        super();
    }

    public FabScrollBehavior(Context context, AttributeSet attrs) {
        Log.d(TAG, "FabScrollBehavior: ");
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
            mTensionFactor = 0.0f;
            mRollingState = RollingFabState.IDLE;
        }
    };

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        Log.d(TAG, "layoutDependsOn: ");
        return super.layoutDependsOn(parent, child, dependency) || dependency instanceof NestedScrollingChild;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

//    @Override
//    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, float velocityX, float velocityY) {
//
//        if (Math.abs(velocityY) < child.getHeight())
//            return false;
//
//        if (velocityY < 0 && mRollingState == RollingFabState.ROLLED_OUT) {
//            Log.d(TAG, "onNestedPreFling: postRollFabInCompletely");
//            postRollFabInCompletely(child);
//        } else if (velocityY > 0 && mRollingState == RollingFabState.IDLE) {
//            Log.d(TAG, "onNestedPreFling: postRollFabOutCompletely");
//            postRollFabOutCompletely(child);
//        }
//
//        return false;
//    }

    private void postRollFabOutCompletely(final FloatingActionButton fab) {
        Log.d(TAG, "postRollFabOutCompletely: ");
        ViewCompat.postOnAnimation(fab, new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(fab)
                        .translationY(mOffscreenTranslation)
                        .setDuration(DURATION)
                        .setInterpolator(LINEAR_INTERPOLATOR)
                        .setListener(mRollingOutListener).start();
            }
        });
    }

    private void postRollFabInCompletely(final FloatingActionButton fab) {
        Log.d(TAG, "postRollFabInCompletely: ");
        ViewCompat.postOnAnimation(fab, new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(fab).translationY(0f).
                        setDuration(DURATION)
                        .setInterpolator(LINEAR_INTERPOLATOR)
                        .setListener(mRollingInListener).start();
            }
        });
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d(TAG, "onNestedPreScroll: dy: " + dy + " ; RollingState: " + mRollingState);

        if(dy > 0 && (mRollingState == RollingFabState.IDLE ||mRollingState == RollingFabState.ROLLING_IN)){
            postRollFabOutCompletely(child);
        }
        else if(dy < 0&& (mRollingState == RollingFabState.IDLE ||mRollingState == RollingFabState.ROLLED_OUT)){
            postRollFabInCompletely(child);
        }
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
        boolean superLayout = super.onLayoutChild(parent, child, layoutDirection);
        mOffscreenTranslation = child.getHeight() + child.getHeight() / 2;
        Log.d(TAG, "onLayoutChild: ");
        return superLayout;
    }


}
