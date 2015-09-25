package com.playtang.android.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.playtang.R;

import static com.playtang.android.init.PlaytangApplication.TAG;
/**
 * Created by prem.k1 on 9/11/2015.
 */
public class GoogleLogin implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    public static final String KEY_IS_RESOLVING = "is_resolving";
    public static final String KEY_SHOULD_RESOLVE = "should_resolve";

    /* Request code used to invoke sign in user interactions. */
    public static final int RC_SIGN_IN = 9001;

    private Activity mActivity;
    private IGoogleLoginListener mGLoginListener;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    public interface IGoogleLoginListener {
        void onGoogleLoginSuccess(Bundle lr);

        void onGoogleLoginCancel();

        void onGoogleLoginError(ConnectionResult CR);

    }

    public GoogleLogin(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
        try {
            mGLoginListener = (IGoogleLoginListener) activity;
            Log.d(TAG, "onAttach adding callback to mActivity ");
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IGoogleLoginListener");
        }
    }

    private void handleLoginSuccess(Bundle loginResult) {
        GoogleManager.getInstance().onGoogleLoginSuccess();
        mGLoginListener.onGoogleLoginSuccess(loginResult);

    }

    private void handleLoginCancel() {
        mGLoginListener.onGoogleLoginCancel();
    }

    private void handleLoginError(ConnectionResult exception) {
        mGLoginListener.onGoogleLoginError(exception);
    }

    private boolean mCheckError = false;
    public void requestGoogleLogin(boolean resolveError) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient = null;
        }
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(new Scope(Scopes.PROFILE))
                    .addScope(new Scope(Scopes.EMAIL))
                    .build();

        mCheckError = resolveError;
        mGoogleApiClient.connect();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "GoogleLogin::onActivityResult calling ");
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "GoogleLogin::onActivityResult OK result code ");
            mShouldResolve = false;
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        String name;
        name = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null ? Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getDisplayName() : " ";
        /*Log.d(TAG, "GoogleLogin::onConnected: SignedIn as : " + name);
        Log.d(TAG, "GoogleLogin::onConnected: Email : " + Plus.AccountApi.getAccountName(mGoogleApiClient));
        Log.d(TAG, "GoogleLogin::onConnected: Url : " + Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getUrl());
        Log.d(TAG, "GoogleLogin::onConnected: Location : " + Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getCurrentLocation());
        Log.d(TAG, "GoogleLogin::onConnected: Name : " + Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getName());
        Log.d(TAG, "GoogleLogin::onConnected: Gender : " + Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getGender());
        Log.d(TAG, "GoogleLogin::onConnected: RelationShip Status : " + Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getRelationshipStatus());
      */  handleLoginSuccess(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "GoogleLogin::onConnectionSuspended:");
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG, "GoogleLogin::onConnectionFailed:" + connectionResult);
        //connectionResult.startResolutionForResult(mActivity, RC_SIGN_IN);


        if (mCheckError && connectionResult.getErrorCode() == ConnectionResult.SIGN_IN_REQUIRED/*!mIsResolving && mShouldResolve*/) {
            mCheckError = false;
            Log.e(TAG, "GoogleLogin::Check if ConnectionResult. has resolution");
            if (connectionResult.hasResolution()) {
                try {
                    Log.e(TAG, "GoogleLogin::startResolutionForResult");
                    connectionResult.startResolutionForResult(mActivity, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "GoogleLogin::Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        }

    }


    private void showErrorDialog(ConnectionResult connectionResult) {
        int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
            // Show the default Google Play services error dialog which may still start an intent
            // on our behalf if the user can resolve the issue.
            GooglePlayServicesUtil.getErrorDialog(errorCode, mActivity, RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mShouldResolve = false;

                        }
                    }).show();
        } else {
            // No default Google Play Services error, display a message to the user.
            String errorString = mContext.getString(R.string.play_services_error_fmt, errorCode);
            Toast.makeText(mContext, errorString, Toast.LENGTH_SHORT).show();
            mShouldResolve = false;

        }
    }

    public void updateState(boolean b1, boolean b2) {
        mIsResolving = b1;
        mShouldResolve = b2;
    }

    public void updateState(boolean b1) {
        mShouldResolve = b1;
    }

}
