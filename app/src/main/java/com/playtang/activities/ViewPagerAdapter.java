package com.playtang.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;

import com.playtang.R;

public class ViewPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] mResources;

    public ViewPagerAdapter(Context context, int [] resources) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = resources;
/*        mResources = new int[]{
                R.drawable.slider0,
                R.drawable.slider,
                R.drawable.slider1,
                R.drawable.slider2
        };*/

    }

    @Override
    public int getCount() {
        return this.mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout itemView = (FrameLayout) mLayoutInflater.inflate(R.layout.item_pager, container, false);
        ImageView img = (ImageView) itemView.findViewById(R.id.pager_image);
        try {
            img.setImageResource(mResources[position]);
        } catch (OutOfMemoryError e) {
            img.setBackgroundColor(Color.TRANSPARENT);
        }
        TextView mPagerText = (TextView) itemView.findViewById(R.id.pager_text);
        //Log.i(TAG, "instantiateItem :: " + position);
        switch (position) {
            case 0:
                break;
            case 1:
                mPagerText.setText(R.string.welcome_playtang);
                break;
            case 2:
                mPagerText.setText(R.string.search_playground);
                break;
            case 3:
                mPagerText.setText(R.string.book_playground);
                break;
        }
        container.addView(itemView, 0);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}