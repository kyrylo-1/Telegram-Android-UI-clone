package com.shorka.telegramclone_ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kyrylo Avramenko on 6/22/2018.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

    private static final String TAG = "RecyclerItemClickListen";
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener listener;
    private GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        this.listener = listener;

//        Log.d(TAG, "RecyclerItemClickListener: ");
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: ");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: ");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && RecyclerItemClickListener.this.listener != null) {
                    RecyclerItemClickListener.this.listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {

        View childView = view.findChildViewUnder(e.getX(), e.getY());
//        Log.d(TAG, "onInterceptTouchEvent: view id: " +view.getId() + " \n childview: "+ childView.getId());

        if (childView != null && listener != null && gestureDetector.onTouchEvent(e)) {
            listener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
