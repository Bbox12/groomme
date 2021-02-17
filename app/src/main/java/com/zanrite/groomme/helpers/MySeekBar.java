package com.zanrite.groomme.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * Created by parag on 23/05/17.
 */
public class MySeekBar extends AppCompatSeekBar {

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }

    Drawable mThumb;

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumb = thumb;
    }

    public Drawable getSeekBarThumb() {
        return mThumb;
    }
}