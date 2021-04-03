package com.example.quwitest.data.network.dto;


import com.google.gson.annotations.SerializedName;

public class CurrentUserResponse {
    @SerializedName("user")
    private UserInfo user;

    public UserInfo getUser() {
        return user;
    }

    public static class UserInfo {
        @SerializedName("name")
        private String name;
        @SerializedName("nick")
        private String nick;
        @SerializedName("email")
        private String email;

        public String getName() {
            return name;
        }

        public String getNick() {
            return nick;
        }

        public String getEmail() {
            return email;
        }
    }
}
