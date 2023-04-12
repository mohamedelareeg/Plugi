package com.plugi.plugi.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.moos.navigation.BottomBarLayout;

import klogi.com.RtlViewPager;

public class CustomTabLayout extends BottomBarLayout {

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setCustomScrollPosition(int position, float positionOffset, boolean updateSelectedText){
        final int roundedPosition = Math.round(position + positionOffset);
        if (roundedPosition < 0 ) {
            return;
        }
        // Update the 'selected state' view as we scroll, if enabled
        if (updateSelectedText) {
            setCurrentTab(roundedPosition);
        }
    }
}
