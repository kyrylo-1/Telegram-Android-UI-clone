package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FabScrollInListBehavior extends FloatingActionButton.Behavior {

    private static final String TAG = "FabScrollInListBehavior";
    private FabHelper fabHelper;

    public FabScrollInListBehavior() {
        super();
    }

    public FabScrollInListBehavior(Context context, AttributeSet attrs) {
//        Log.d(TAG, "FabScrollInListBehavior: ");
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
//        Log.d(TAG, "layoutDependsOn: ");
        return super.layoutDependsOn(parent, child, dependency) || dependency instanceof NestedScrollingChild;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d(TAG, "onNestedPreScroll: dy: " + dy + " ; RollingState: " + fabHelper.getRollingState());

        if (dy > 0 && (fabHelper.getRollingState() == RollingFabState.IDLE || fabHelper.getRollingState() == RollingFabState.ROLLING_IN)) {
            fabHelper.postRollFabOutCompletely(child);
        } else if (dy < 0 && (fabHelper.getRollingState() == RollingFabState.IDLE || fabHelper.getRollingState() == RollingFabState.ROLLED_OUT)) {
            fabHelper.postRollFabInCompletely(child);
        }
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
        boolean superLayout = super.onLayoutChild(parent, child, layoutDirection);
        if (fabHelper == null)
            fabHelper = new FabHelper(child, 300L);

//        Log.d(TAG, "onLayoutChild: ");
        return superLayout;
    }


}
