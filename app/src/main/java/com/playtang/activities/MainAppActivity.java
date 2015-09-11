package com.playtang.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.playtang.R;
import com.playtang.android.login.FaceBookLogin;
import com.playtang.android.login.GoogleLogin;

import static com.playtang.android.login.GoogleLogin.KEY_IS_RESOLVING;
import static com.playtang.android.login.GoogleLogin.KEY_SHOULD_RESOLVE;

/*
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import java.security.MessageDigest;
import android.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
*/



public class MainAppActivity extends AppCompatActivity implements
        FaceBookLogin.IFaceBookLoginListener, GoogleLogin.IGoogleLoginListener {

    private static final String TAG = "MainAppActivity";

    private FaceBookLogin fb;
    private GoogleLogin gl;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private LinearLayout mPagerMarkView;
    private static int mPreviousPage = 0;
    private TextView plusBtn;
    private TextView fbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        initLayout();
        if (savedInstanceState != null) {
            if (gl != null) {
                gl.updateState(savedInstanceState.getBoolean(KEY_IS_RESOLVING), savedInstanceState.getBoolean(KEY_SHOULD_RESOLVE));
            }

        }

    }


    private void initLayout() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerMarkView = (LinearLayout) findViewById(R.id.page_mark);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int display_width = displaymetrics.widthPixels;
        int getDisplay_hieght = displaymetrics.heightPixels;
        int i = (display_width * 99) / 800;
        mViewPager.getLayoutParams().height = ((getDisplay_hieght - getStatusBarHeight()) - (i * 2)) - ((display_width / 20) * 7);

        mViewPagerAdapter = new ViewPagerAdapter(this);
        mViewPager.setAdapter(mViewPagerAdapter);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        plusBtn = (TextView) findViewById(R.id.plus_sign_in_button);
        fbBtn = (TextView) findViewById(R.id.fb_login);

        plusBtn.setTypeface(Typeface.createFromAsset(getAssets(), "font/Catull.ttf"));
        fbBtn.setTypeface(Typeface.createFromAsset(getAssets(), "font/klavika.ttf"));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gl == null) {
                    gl = new GoogleLogin(MainAppActivity.this, MainAppActivity.this);
                }
                gl.updateState(true);
                gl.requestGoogleLogin();
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KeyHash:", "fbBtn onclick");
                if (fb == null) {
                    fb = new FaceBookLogin(MainAppActivity.this, MainAppActivity.this);
                }
                fb.requestFacebookLogin();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void getFacebookSHA1Key() {
        /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        */
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initPagerMarkView();
        setPagerPageChangeListener();
    }

    private void setPagerPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerMarkView.getChildAt(mPreviousPage).setBackgroundColor(Color.WHITE);
                mPagerMarkView.getChildAt(position).setBackgroundColor(Color.YELLOW);
                mPreviousPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    private void initPagerMarkView() {
        mPagerMarkView.removeAllViews();
        int n = mViewPagerAdapter.getCount();
        for (int i = 0; i < n; i++) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
            tv.setText(".");
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(45);
            lp.width = 15;
            lp.height = 10;
            lp.leftMargin = 8;
            tv.setBackgroundColor(Color.WHITE);
            if (i == mPreviousPage) {
                tv.setBackgroundColor(Color.YELLOW);
            }
            tv.setLayoutParams(lp);
            mPagerMarkView.addView(tv);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState:");
/*        gl.updateState(); TO DO --Check if needed
        outState.putBoolean(KEY_IS_RESOLVING, mIsResolving);
        outState.putBoolean(KEY_SHOULD_RESOLVE, mShouldResolve);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == GoogleLogin.RC_SIGN_IN) {
            gl.onActivityResult(requestCode, resultCode, data);
            //mIsResolving = false;
            //mGoogleApiClient.connect();
        } else {
            // 64206
            fb.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void launchHomeActivity() {
        Intent mHomeIntent = new Intent(MainAppActivity.this, AppHomeActivity.class);
        startActivity(mHomeIntent);
        finish();
    }


    public void onFaceBookLoginSuccess(LoginResult lr) {
        launchHomeActivity();
    }

    public void onFaceBookLoginCancel() {
    }

    private void showErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Oops");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage("Some Problem Occurred in Login.PLz Try Later");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainAppActivity.this.finish();
            }
        });
        alertDialog.show();
    }

    public void onFaceBookLoginError(FacebookException e) {
        showErrorDialog();
    }

    @Override
    public void onGoogleLoginSuccess(Bundle bundle) {
        launchHomeActivity();
    }

    @Override
    public void onGoogleLoginCancel() {

    }

    @Override
    public void onGoogleLoginError(ConnectionResult cr) {
        showErrorDialog();
    }


}
