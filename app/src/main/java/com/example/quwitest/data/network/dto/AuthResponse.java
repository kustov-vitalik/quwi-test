package com.example.quwitest.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
