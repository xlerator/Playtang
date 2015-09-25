package com.playtang.android.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by d.dekivadiya on 15/09/2015.
 */
public class IconView extends TextView {
    public IconView(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(),"font/zombats-app.ttf"));
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "font/zombats-app.ttf"));
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(Typeface.createFromAsset(context.getAssets(),"font/zombats-app.ttf"));
    }


}
