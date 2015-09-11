package com.playtang.android.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.playtang.android.login.LoginType;

public class PlaytangPreferenceManager
{
	public static final String KEY_LAST_LOGIN_TYPE;
	public static final String KEY_LOGIN_ACCESS_EXPIRES;
	public static final String KEY_LOGIN_ACCESS_TOKEN;

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
}