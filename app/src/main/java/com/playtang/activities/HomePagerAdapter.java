package com.playtang.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.playtang.R;
/**
 * Created by d.dekivadiya on 15/09/2015.
 */
public class HomePagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] mResources;


    public HomePagerAdapter(Context context, int[] resources) {
        mResources = resources;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (FrameLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout mHomePager = (FrameLayout) mLayoutInflater.inflate(R.layout.item_home_pager,container,false);
        ImageView img = (ImageView) mHomePager.findViewById(R.id.pager_image);
       // try {
            img.setImageResource(mResources[position]);
     /*  } catch (OutOfMemoryError e) {
            Log.i("darpan","OutOfMemoryError ");
            img.setBackgroundColor(Color.TRANSPARENT);
        }
     */
        container.addView(mHomePager, 0);
        return mHomePager;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
