package com.playtang.android.login;

import com.playtang.android.config.PlaytangPreferenceManager;

/**
 * Created by prem.k1 on 9/11/2015.
 */
public class GoogleManager {

    private static GoogleManager mGlManager;

    private GoogleManager(){}

    public static GoogleManager getInstance() {
        synchronized (GoogleManager.class) {
            if (mGlManager == null) {
                mGlManager = new GoogleManager();
            }
            return mGlManager;
        }
    }

    public void onGoogleLoginSuccess() {
        PlaytangPreferenceManager.getInstance().saveLastLoginType(LoginType.GOOGLE);
    }


}
