package com.playtang.android.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by prem.k1 on 9/11/2015.
 */
public class FaceBookLogin {

    private Context mContext;
    private CallbackManager callbackManager;

    private Activity mActivity;
    private IFaceBookLoginListener mFBLoginListener;

    private static final String TAG = "FaceBookLogin";

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
                        Log.d(TAG, "onSuccess");
                        handleLoginSuccess(loginResult);
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
                });
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("public_profile", "user_friends"));
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
