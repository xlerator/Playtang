package com.playtang.android.login;

import com.playtang.android.config.PlaytangPreferenceManager;

/**
 * Created by prem.k1 on 9/11/2015.
 */
public class FaceBookManager {

    private static FaceBookManager mFBManager;

    private FaceBookManager(){}

    public static FaceBookManager getInstance() {
        synchronized (FaceBookManager.class) {
            if (mFBManager == null) {
                mFBManager = new FaceBookManager();
            }
            return mFBManager;
        }
    }

    public void onFaceBookLoginSuccess() {
        PlaytangPreferenceManager.getInstance().saveLastLoginType(LoginType.FACEBOOK);
    }


}
