package com.zanrite.groomme.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ExpandedBottomSheetBehavior<V extends View> extends
        BottomSheetBehavior<V> {

    public ExpandedBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(final CoordinatorLayout parent, final V child, final int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(parent, child, event);
        } catch (NullPointerException ignored) {
            return false;
        }
    }
}
