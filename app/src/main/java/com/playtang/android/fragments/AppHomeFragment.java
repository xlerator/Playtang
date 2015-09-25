package com.playtang.android.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.playtang.R;
import com.playtang.activities.HomePagerAdapter;

/**
 * Created by d.dekivadiya on 16/09/2015.
 */
public class AppHomeFragment extends Fragment {
    private static int mPreviousPage = 0;
    Activity activity;
    int[] mResources;
    private ViewPager mViewPager;
    private HomePagerAdapter mHomePagerAdapter;
    private LinearLayout dots;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("darpan", "onCreate");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("darpan", "onActivityCreated");
        view = getView();
        activity = getActivity();
        initViewPager();
        initdots();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("darpan", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        return rootView;
    }

    private void initViewPager() {
        Log.i("darpan", "initViewPager");
        mResources = new int[]{R.drawable.slider0,
                R.drawable.slider,
                R.drawable.slider1,
                R.drawable.slider2};
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int display_width = displaymetrics.widthPixels;
        int display_hieght = displaymetrics.heightPixels;
        mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
        mViewPager.getLayoutParams().height = ((display_hieght * 5) / 16) + getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
        mHomePagerAdapter = new HomePagerAdapter(activity, mResources);
        mViewPager.setAdapter(mHomePagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("darpan", "onPageSelected " + position);
                ImageView imageView = (ImageView) dots.getChildAt(mPreviousPage);
                imageView.setImageResource(R.drawable.pager_dot_white);
                imageView = (ImageView) dots.getChildAt(position);
                imageView.setImageResource(R.drawable.pager_dot_selected);
                mPreviousPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    private void initdots() {
        dots = (LinearLayout) view.findViewById(R.id.dots);
        dots.removeAllViews();
        int i = 0;
        int size = mResources.length;
        while (i < size) {
            ImageView mImageView = new ImageView(activity.getApplicationContext());
            if (i != mPreviousPage) {
                mImageView.setImageResource(R.drawable.pager_dot_white);

            } else {
                mImageView.setImageResource(R.drawable.pager_dot_selected);
                // mImageView.setPadding(5, 5, 5, 5);
            }
            mImageView.setPadding(10, 10, 10, 10);
            dots.addView(mImageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.gravity = Gravity.CENTER;
            mImageView.setLayoutParams(params);
            i++;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
