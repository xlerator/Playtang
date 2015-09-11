package com.playtang.android.init;


import android.app.Application;

import com.playtang.android.config.PlaytangPreferenceManager;

public class PlaytangApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        PlaytangPreferenceManager.getInstance().initialize(getApplicationContext());
    }
}