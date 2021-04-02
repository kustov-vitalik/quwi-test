package com.example.quwitest.data.local.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quwitest.R;

public class TokenStorage {
    private final SharedPreferences sharedPreferences;
    private static final String TOKEN_NAME_KEY = "AuthToken";
    private static TokenStorage instance;

    public static TokenStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (TokenStorage.class) {
                if (instance == null) {
                    instance = new TokenStorage(context);
                }
            }
        }
        return instance;
    }

    private TokenStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void storeToken(String authToken) {
        sharedPreferences.edit().putString(TOKEN_NAME_KEY, authToken).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_NAME_KEY, null);
    }

    public Boolean hasToken() {
        return sharedPreferences.contains(TOKEN_NAME_KEY) && sharedPreferences.getString(TOKEN_NAME_KEY, null) != null;
    }

    public void removeToken() {
        sharedPreferences.edit().remove(TOKEN_NAME_KEY).apply();
    }
}
