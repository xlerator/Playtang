package com.playtang.android.login;

import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.playtang.android.config.PlaytangPreferenceManager;
import java.util.Date;
import java.util.Set;

import static com.playtang.android.init.PlaytangApplication.TAG;

/**
 * Created by prem.k1 on 9/11/2015.
 */
public class FaceBookManager {

    private FaceBookProfile mFBProfile;
    private static FaceBookManager mFBManager;

    private FaceBookAccessToken mFBToken;
    private FaceBookManager(){}

    public static FaceBookManager getInstance() {
        synchronized (FaceBookManager.class) {
            if (mFBManager == null) {
                mFBManager = new FaceBookManager();
            }
            return mFBManager;
        }
    }

    private AccessTokenTracker mATokenTracker;
    private ProfileTracker mProfileTracker;

    public void init(Context context) {
        FacebookSdk.sdkInitialize(context);

        mATokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d(TAG, "FaceBookManager::onCurrentAccessTokenChanged oldAccessToken="+oldAccessToken);
                Log.d(TAG, "FaceBookManager::onCurrentAccessTokenChanged currentAccessToken="+currentAccessToken);
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d(TAG, "FaceBookManager::onCurrentAccessTokenChanged oldProfile="+oldProfile);
                Log.d(TAG, "FaceBookManager::onCurrentAccessTokenChanged currentProfile="+currentProfile);
                createOrUpdateFBProfile(currentProfile);
                saveProfileToPreferences();
            }
        };

        mATokenTracker.startTracking();
        mProfileTracker.startTracking();

        initFBAccessToken();
        initFBProfile();

        Log.d(TAG, "FaceBookManager:: Init mFBProfile==" + mFBProfile);
        Log.d(TAG,"FaceBookManager:: Init mFBToken=="+mFBToken);

        AccessToken aToken = new  AccessToken(mFBToken.getAccessToken(),mFBToken.getApplicationId(),
                mFBToken.getUserId(),mFBToken.getPermissions(), mFBToken.getDeclinedPermissions(),
                mFBToken.getAccessTokenSource(),mFBToken.getExpirationTime(),mFBToken.getLastRefreshTime());

        AccessToken.setCurrentAccessToken(aToken);

    }

    public void onFaceBookLoginSuccess() {
        Log.d(TAG, "FaceBookManager:: onFaceBookLoginSuccess # 0 ");
        PlaytangPreferenceManager.getInstance().saveLastLoginType(LoginType.FACEBOOK);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Log.d(TAG, "FaceBookManager:: onFaceBookLoginSuccess # 1 accessToken==" + accessToken);
        createOrUpdateFBAccessToken(accessToken);
        saveAccessTokenToPreferences();
        Log.d(TAG, "FaceBookManager:: onFaceBookLoginSuccess # 2 mFBToken==" + mFBToken);

        if (mProfileTracker == null) {
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    Log.d(TAG, "FaceBookManager::onFaceBookLoginSuccess onCurrentAccessTokenChanged oldProfile="+oldProfile);
                    Log.d(TAG, "FaceBookManager::onFaceBookLoginSuccess onCurrentAccessTokenChanged currentProfile="+currentProfile);
                    createOrUpdateFBProfile(currentProfile);
                    saveProfileToPreferences();
                }
            };
        }

        mProfileTracker.stopTracking();
        mProfileTracker.startTracking();

        Profile.fetchProfileForCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        Log.d(TAG, "FaceBookManager:: onFaceBookLoginSuccess # 3 restaring tracker profile==" + profile);
        if (profile != null ) {
            createOrUpdateFBProfile(profile);
            saveProfileToPreferences();
        }
        Log.d(TAG, "FaceBookManager:: onFaceBookLoginSuccess # 4 mFBProfile==" + mFBProfile);

    }

    /*
      FaceBook Access Token  API Start
     */

    private void initFBAccessToken() {
        if (mFBToken == null) {
            mFBToken = new FaceBookAccessToken();
        }
        mFBToken.setAccessToken(PlaytangPreferenceManager.getInstance().getFBAccessToken());
        mFBToken.setApplicationId(PlaytangPreferenceManager.getInstance().getFBApplicationId());
        mFBToken.setUserId(PlaytangPreferenceManager.getInstance().getFBUserId());

        mFBToken.setPermissions(PlaytangPreferenceManager.getInstance().getFBPermissions());
        mFBToken.setDeclinedPermissions(PlaytangPreferenceManager.getInstance().getFBDeclinedPermissions());

        mFBToken.setAccessTokenSource(getAccessTokenSourceInner(PlaytangPreferenceManager.getInstance().getFBAccessTokenSource()));
        mFBToken.setExpirationTime(new Date(PlaytangPreferenceManager.getInstance().getFBExpirationTime()));
        mFBToken.setLastRefreshTime(new Date(PlaytangPreferenceManager.getInstance().getFBLastRefreshTime()));
    }

    private AccessTokenSource getAccessTokenSourceInner(String str) {
        if (str == "" | str == null) {
            return AccessTokenSource.FACEBOOK_APPLICATION_SERVICE;
        } else if ("FACEBOOK_APPLICATION_WEB".equals(str)) {
            return AccessTokenSource.FACEBOOK_APPLICATION_WEB;
        } else if ("FACEBOOK_APPLICATION_NATIVE".equals(str)) {
            return AccessTokenSource.FACEBOOK_APPLICATION_NATIVE;
        } else if ("FACEBOOK_APPLICATION_SERVICE".equals(str)) {
            return AccessTokenSource.FACEBOOK_APPLICATION_SERVICE;
        }
        return AccessTokenSource.FACEBOOK_APPLICATION_WEB;
    }

    private void createOrUpdateFBAccessToken(AccessToken accessToken) {
        if (mFBToken == null) {
            mFBToken = new FaceBookAccessToken();
        }
        mFBToken.setAccessToken(accessToken.getToken());
        mFBToken.setApplicationId(accessToken.getApplicationId());
        mFBToken.setUserId(accessToken.getUserId());
        mFBToken.setPermissions(accessToken.getPermissions());
        mFBToken.setDeclinedPermissions(accessToken.getDeclinedPermissions());
        mFBToken.setAccessTokenSource(accessToken.getSource());
        mFBToken.setExpirationTime(accessToken.getExpires());
        mFBToken.setLastRefreshTime(accessToken.getLastRefresh());
    }

    private void saveAccessTokenToPreferences() {
        PlaytangPreferenceManager.getInstance().saveFBAccessToken(mFBToken.getAccessToken());
        PlaytangPreferenceManager.getInstance().saveFBApplicationId(mFBToken.getApplicationId());
        PlaytangPreferenceManager.getInstance().saveFBUserId(mFBToken.getUserId());

        PlaytangPreferenceManager.getInstance().saveFBPermissions((Set<String>) mFBToken.getPermissions());
        PlaytangPreferenceManager.getInstance().saveFBDeclinedPermissions((Set<String>) mFBToken.getDeclinedPermissions());

        PlaytangPreferenceManager.getInstance().saveFBAccessTokenSource(mFBToken.getAccessTokenSource().toString());
        PlaytangPreferenceManager.getInstance().saveFBExpirationTime((mFBToken.getExpirationTime()).getTime());
        PlaytangPreferenceManager.getInstance().saveFBLastRefreshTime((mFBToken.getLastRefreshTime()).getTime());
    }


    /*
      FaceBook Access Token  API End
     */


    /*
      FaceBook Profile API Start
     */

    private void initFBProfile() {
        if (mFBProfile == null) {
            mFBProfile = new FaceBookProfile();
        }
        mFBProfile.setId(PlaytangPreferenceManager.getInstance().getFBId());
        mFBProfile.setFirstName(PlaytangPreferenceManager.getInstance().getFBFirstName());
        mFBProfile.setMiddleName(PlaytangPreferenceManager.getInstance().getFBMiddleName());
        mFBProfile.setLastName(PlaytangPreferenceManager.getInstance().getFBLastName());
        mFBProfile.setName(PlaytangPreferenceManager.getInstance().getFBName());
        //mFBProfile.setLinkUri();
    }

    private void createOrUpdateFBProfile(Profile profile) {
        if (mFBProfile == null) {
            mFBProfile = new FaceBookProfile();
        }
        mFBProfile.setId(profile.getId());
        mFBProfile.setFirstName(profile.getFirstName());
        mFBProfile.setMiddleName(profile.getMiddleName());
        mFBProfile.setLastName(profile.getLastName());
        mFBProfile.setName(profile.getName());
        mFBProfile.setLinkUri(profile.getLinkUri());
    }


    private void saveProfileToPreferences() {
        PlaytangPreferenceManager.getInstance().saveFBId(mFBProfile.getId());
        PlaytangPreferenceManager.getInstance().saveFBFirstName(mFBProfile.getFirstName());
        PlaytangPreferenceManager.getInstance().saveFBMiddleName(mFBProfile.getMiddleName());
        PlaytangPreferenceManager.getInstance().saveFBLastName(mFBProfile.getLastName());
        PlaytangPreferenceManager.getInstance().saveFBName(mFBProfile.getName());
    }

    public String getFBId()
    {
        return mFBProfile.getId();
    }

    public String getFBFirstName()
    {
        return mFBProfile.getFirstName();
    }
    public String getFBLastName()
    {
        return mFBProfile.getLastName();
    }
    public String getFBMiddleName()
    {
        return mFBProfile.getMiddleName();
    }
    public String getFBName()
    {
        return mFBProfile.getName();
    }

    public FaceBookProfile getFBProfile() {
        return mFBProfile;
    }

    /*
      FaceBook Profile API End
    */
}
