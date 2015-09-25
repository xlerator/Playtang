package com.playtang.android.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.playtang.android.login.LoginType;

import java.util.Set;

public class PlaytangPreferenceManager
{
	public static final String KEY_LAST_LOGIN_TYPE;
	public static final String KEY_LOGIN_ACCESS_EXPIRES;
	public static final String KEY_LOGIN_ACCESS_TOKEN;

	private static final String FACEBOOK_ID_KEY = a("fb_id");
	private static final String FACEBOOK_FIRST_NAME_KEY = a("fb_first_name");
	private static final String FACEBOOK_MIDDLE_NAME_KEY = a("fb_middle_name");
	private static final String FACEBOOK_LAST_NAME_KEY = a("fb_last_name");
	private static final String FACEBOOK_NAME_KEY = a("fb_name");
	private static final String FACEBOOK_LINK_URI_KEY = a("fb_link_uri");

    public static final String FACEBOOK_USER_ID_KEY = a("user_id");
    private static final String FACEBOOK_EXPIRES_AT_KEY = a("expires_at");
    private static final String FACEBOOK_PERMISSIONS_KEY = a("permissions");
    private static final String FACEBOOK_DECLINED_PERMISSIONS_KEY = a("declined_permissions");
    private static final String FACEBOOK_TOKEN_KEY = a("token");
    private static final String FACEBOOK_SOURCE_KEY = a("source");
    private static final String FACEBOOK_LAST_REFRESH_KEY = a("last_refresh");
    private static final String FACEBOOK_APPLICATION_ID_KEY = a("application_id");


	private SharedPreferences mSharedPref;

    static
	{
		KEY_LAST_LOGIN_TYPE = a("last_login_type");
		KEY_LOGIN_ACCESS_TOKEN = a("login_access_token");
		KEY_LOGIN_ACCESS_EXPIRES = a("login_access_expires");
	}
	
	private static String a(String paramString)
	{
		return "com.playtang.app.ecom." + paramString;
	}
	
	private static PlaytangPreferenceManager mPreferenceManager;
	
	private PlaytangPreferenceManager(){}
	
	public static PlaytangPreferenceManager getInstance() {
        synchronized (PlaytangPreferenceManager.class) {
            if (mPreferenceManager == null) {
                mPreferenceManager = new PlaytangPreferenceManager();
            }
            return mPreferenceManager;
        }
    }

	public void initialize(Context context){
		mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
	}



    /*
       Login API Start
     */
	public LoginType getLastLoginType()
	{
		return getLoginType(this.mSharedPref.getInt(KEY_LAST_LOGIN_TYPE, 0));
	}

	public void saveLastLoginType(LoginType lt) {
		SharedPreferences.Editor editor = this.mSharedPref.edit();
		editor.putInt(KEY_LAST_LOGIN_TYPE, getLoginValue(lt));
		editor.commit();
	}

	public int getLoginValue(LoginType lt) {
		if (lt == LoginType.DIRECT) {
			return 3;
		} else if (lt == LoginType.FACEBOOK) {
			return 2;
		} else if (lt == LoginType.GOOGLE) {
			return 1;
		} else return 0;
	}

	public LoginType getLoginType(int num){
		if (num == 0) {
			return LoginType.UNKNOWN;
		} else if (num == 1) {
			return LoginType.GOOGLE;
		} else if (num == 2) {
			return LoginType.FACEBOOK;
		} else if (num == 3) {
			return LoginType.DIRECT;
		} else
			return LoginType.UNKNOWN;
	}

    /*
       Login API End
    */

    /*
       FaceBook API Start
    */








    public String getFBId()
    {
        return this.mSharedPref.getString(FACEBOOK_ID_KEY, "");
    }

    public void saveFBId(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_ID_KEY, str);
        editor.commit();
    }

    public String getFBFirstName()
    {
        return this.mSharedPref.getString(FACEBOOK_FIRST_NAME_KEY, "");
    }

    public void saveFBFirstName(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_FIRST_NAME_KEY, str);
        editor.commit();
    }

    public String getFBMiddleName()
    {
        return this.mSharedPref.getString(FACEBOOK_MIDDLE_NAME_KEY, "");
    }

    public void saveFBMiddleName(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_MIDDLE_NAME_KEY, str);
        editor.commit();
    }

    public String getFBLastName()
    {
        return this.mSharedPref.getString(FACEBOOK_LAST_NAME_KEY, "");
    }

    public void saveFBLastName(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_LAST_NAME_KEY, str);
        editor.commit();
    }

    public String getFBName()
    {
        return this.mSharedPref.getString(FACEBOOK_NAME_KEY, "");
    }

    public void saveFBName(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_NAME_KEY, str);
        editor.commit();
    }


    /*



     */

    public String getFBAccessToken() {
        return this.mSharedPref.getString(FACEBOOK_TOKEN_KEY, "");
    }
    public void saveFBAccessToken(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_TOKEN_KEY, str);
        editor.commit();
    }

    public String getFBApplicationId() {
        return this.mSharedPref.getString(FACEBOOK_APPLICATION_ID_KEY, "");
    }
    public void saveFBApplicationId(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_APPLICATION_ID_KEY, str);
        editor.commit();
    }

    public String getFBUserId() {
        return this.mSharedPref.getString(FACEBOOK_USER_ID_KEY, "");
    }

    public void saveFBUserId(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_USER_ID_KEY, str);
        editor.commit();
    }

    public Set<String> getFBPermissions() {
        return this.mSharedPref.getStringSet(FACEBOOK_PERMISSIONS_KEY, null);
    }

    public void saveFBPermissions(Set<String> str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putStringSet(FACEBOOK_PERMISSIONS_KEY, str);
        editor.commit();
    }

    public Set<String> getFBDeclinedPermissions() {
        return this.mSharedPref.getStringSet(FACEBOOK_DECLINED_PERMISSIONS_KEY, null);
    }

    public void saveFBDeclinedPermissions(Set<String> str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putStringSet(FACEBOOK_DECLINED_PERMISSIONS_KEY, str);
        editor.commit();
    }

    public String getFBAccessTokenSource() {
        return this.mSharedPref.getString(FACEBOOK_SOURCE_KEY, "");
    }

    public void saveFBAccessTokenSource(String str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putString(FACEBOOK_SOURCE_KEY, str);
        editor.commit();
    }

    public Long getFBExpirationTime() {
        return this.mSharedPref.getLong(FACEBOOK_EXPIRES_AT_KEY, Long.MAX_VALUE);
    }

    public void saveFBExpirationTime(Long str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putLong(FACEBOOK_EXPIRES_AT_KEY, str);
        editor.commit();
    }

    public Long getFBLastRefreshTime() {
        return this.mSharedPref.getLong(FACEBOOK_LAST_REFRESH_KEY, Long.MAX_VALUE);
    }

    public void saveFBLastRefreshTime(Long str) {
        SharedPreferences.Editor editor = this.mSharedPref.edit();
        editor.putLong(FACEBOOK_LAST_REFRESH_KEY, str);
        editor.commit();
    }


}