package com.example.quwitest.data.local.sharedpreferences;

import android.content.SharedPreferences;

public class TokenStorage {
    private final SharedPreferences sharedPreferences;
    private static final String TOKEN_NAME_KEY = "AuthToken";

    public TokenStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
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
