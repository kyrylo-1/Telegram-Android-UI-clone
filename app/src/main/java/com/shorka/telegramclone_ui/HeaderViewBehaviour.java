package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Kyrylo Avramenko on 6/13/2018.
 */
public class HeaderViewBehaviour extends CoordinatorLayout.Behavior<HeaderView> {

    private static final String TAG = "HeaderViewBehaviour";
    private Context context;

    private int startMarginLeft;
    private int endMarginLeft;
    private int mMarginRight;
    private int mStartMarginBottom;
    private float titleStartSize;
    private float titleEndSize;

    private int mImageStartScale, mImageEndScale;

    private boolean isHide;

    public HeaderViewBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        shouldInitProperties();
    }

    public HeaderViewBehaviour(Context context, AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.context = mContext;
        shouldInitProperties();
    }

    public static int getToolbarHeight(Context context) {
        int result = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return result;

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, HeaderView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    int scrollCoeff = 2;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, HeaderView child, View dependency) {
        shouldInitProperties();

        int maxScroll = ((AppBarLayout) dependency).getTotalScrollRange();
//        Log.d(TAG, "onDependentViewChanged: maxScroll: " + maxScroll);


        float percentage = Math.abs(dependency.getY()) / (float) maxScroll;
        float childPosition = dependency.getHeight()
                + dependency.getY()
                - child.getHeight()
                - (getToolbarHeight(context) - child.getHeight()) * percentage / 1;

//        Log.d(TAG, "onDependentViewChanged: "+
//                "\n dependency.getY():" +  Math.abs(dependency.getY()) +
//                "\n childPos: " + childPosition +
//                "\n child.getHeight: " + child.getHeight() +
//                "\n percentage: " + percentage
//        );

        childPosition = childPosition - mStartMarginBottom * (1f - percentage);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (Math.abs(dependency.getY()) >= maxScroll / 100) {
            float layoutPercentage = (Math.abs(dependency.getY()) - (maxScroll / 2)) / (maxScroll / 2);


            int leftMargin = (int) (layoutPercentage * endMarginLeft) + startMarginLeft;
            lp.leftMargin = leftMargin;
            float textSize = getTranslationOffset(titleStartSize, titleEndSize, layoutPercentage);

//            Log.d(TAG, "onDependentViewChanged:"
//                    + "\n leftMargin: " + leftMargin
//                    + "\n textSize: " + textSize
//                    + "\n layoutPercentage: " + layoutPercentage
//                    + "\n titleStartSize: " + titleStartSize
//                    + "\n titleEndSize: " + titleEndSize);

            child.setTextSize(textSize);
        } else {
            lp.leftMargin = startMarginLeft;
        }

//        float layoutPercentage = (Math.abs(dependency.getY()) - (maxScroll / 1)) / Math.abs(maxScroll / 1);
//
//        Log.d(TAG, "onDependentViewChanged: layoutPercentage: " + layoutPercentage);
//
//        lp.leftMargin = (int) (layoutPercentage * endMarginLeft) + startMarginLeft;
//        child.setTextSize(getTranslationOffset(titleStartSize, titleEndSize, layoutPercentage));


        lp.rightMargin = mMarginRight;
        child.setLayoutParams(lp);
        child.setY(childPosition);

        if (isHide && percentage < 1) {
            child.setVisibility(View.VISIBLE);
            isHide = false;
        } else if (!isHide && percentage == 1) {
            child.setVisibility(View.GONE);
            isHide = true;
        }
        return true;
    }

    protected float getTranslationOffset(float expandedOffset, float collapsedOffset, float ratio) {

        return expandedOffset + ratio * (collapsedOffset - expandedOffset);
    }

    private void shouldInitProperties() {
        if (startMarginLeft == 0) {
            startMarginLeft = context.getResources().getDimensionPixelOffset(R.dimen.header_view_start_margin_left);
        }

        if (endMarginLeft == 0) {
            endMarginLeft = context.getResources().getDimensionPixelOffset(R.dimen.header_view_end_margin_left);
        }

        if (mStartMarginBottom == 0) {
            mStartMarginBottom = context.getResources().getDimensionPixelOffset(R.dimen.header_view_start_margin_bottom);
        }

        if (mMarginRight == 0) {
            mMarginRight = context.getResources().getDimensionPixelOffset(R.dimen.header_view_end_margin_right);
        }

        if (titleEndSize == 0) {
            titleEndSize = context.getResources().getDimensionPixelSize(R.dimen.header_view_end_text_size);
        }

        if (titleStartSize == 0) {
            titleStartSize = context.getResources().getDimensionPixelSize(R.dimen.header_view_start_text_size);
        }

//        if(mImageStartScale == 0)
//            mImageStartScale = context.getResources().getDimensionPixelSize(R.dimen.image_scale);
//
//        if(mImageEndScale == 0)
//            mImageEndScale = context.getResources().getDimensionPixelSize(R.dimen.image_scale_toolbar);
    }
}
