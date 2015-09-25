package com.playtang.android.init;


import android.app.Application;

import com.playtang.android.config.PlaytangPreferenceManager;
import com.playtang.android.login.FaceBookManager;
import com.playtang.android.login.LoginType;

public class PlaytangApplication extends Application{

    public static final String TAG = "PlayTang";

    @Override
    public void onCreate() {
        super.onCreate();

        //
        PlaytangPreferenceManager mPref = PlaytangPreferenceManager.getInstance();
        mPref.initialize(getApplicationContext());

        //
        if (mPref.getLastLoginType() == LoginType.FACEBOOK) {
            FaceBookManager.getInstance().init(getApplicationContext());
        }
    }
}