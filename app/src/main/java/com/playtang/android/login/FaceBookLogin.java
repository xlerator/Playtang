package com.playtang.android.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import static com.playtang.android.init.PlaytangApplication.TAG;

/**
 * Created by prem.k1 on 9/11/2015.
 */
public class FaceBookLogin {

    private Context mContext;
    private CallbackManager callbackManager;

    private Activity mActivity;
    private IFaceBookLoginListener mFBLoginListener;

    public static final int FACEBOOK_LOGIN_REQUEST_CODE = 64206;//CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();;
    // TO DO initialze in facebook application and uncomment

    public interface IFaceBookLoginListener {
        void onFaceBookLoginSuccess(LoginResult lr);

        void onFaceBookLoginCancel();

        void onFaceBookLoginError(FacebookException e);

    }

    public FaceBookLogin(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
        try {
            mFBLoginListener = (IFaceBookLoginListener) activity;
            Log.d(TAG, "onAttach adding callback to mActivity ");
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void requestFacebookLogin() {
        FacebookSdk.sdkInitialize(mContext.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "FaceBookLogin:: onSuccess result = "+loginResult);
                        handleLoginSuccess(loginResult);
                        checkPermissions();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "onCancel");
                        handleLoginCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(TAG, "onError" + exception);
                        handleLoginError(exception);
                    }
                });//  "public_profile", "user_friends" "publish_actions"
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("public_profile", "user_friends"));
    }


    private void checkPermissions() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            Log.d(TAG, "Access token 0A getPermissions ==" + accessToken.getPermissions());
            Log.d(TAG, "Access token 0A getApplicationId ==" + accessToken.getApplicationId());
            Log.d(TAG, "Access token 0A getUserId =="+accessToken.getUserId());
            Log.d(TAG, "Access token 0A getDeclinedPermissions =="+accessToken.getDeclinedPermissions());
            Log.d(TAG, "Access token 0A getExpires =="+accessToken.getExpires());
            Log.d(TAG, "Access token 0A getLastRefresh =="+accessToken.getLastRefresh());
            Log.d(TAG, "Access token 0A getSource =="+accessToken.getSource());
            Log.d(TAG, "Access token 0A isExpired =="+accessToken.isExpired());
        }


    }

    private void handleLoginSuccess(LoginResult loginResult) {
        FaceBookManager.getInstance().onFaceBookLoginSuccess();
        mFBLoginListener.onFaceBookLoginSuccess(loginResult);
    }

    private void handleLoginCancel() {
        mFBLoginListener.onFaceBookLoginCancel();
    }

    private void handleLoginError(FacebookException exception) {
        mFBLoginListener.onFaceBookLoginError(exception);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

/*
*
getPermissions ==[public_profile, user_friends] read permission
getPermissions ==[public_profile, publish_actions, user_friends]   publish permission
getApplicationId ==1192310510785841
getUserId ==1693027964249473
getDeclinedPermissions ==[]
getExpires ==Tue Nov 10 08:57:49 GMT+00:00 2015
getLastRefresh ==Sat Sep 12 06:38:41 GMT+00:00 2015
getSource ==FACEBOOK_APPLICATION_SERVICE
isExpired ==false
* */
