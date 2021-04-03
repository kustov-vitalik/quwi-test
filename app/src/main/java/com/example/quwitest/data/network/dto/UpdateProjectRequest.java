package com.example.quwitest.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class UpdateProjectRequest {
    @SerializedName("name")
    private String name;

    public UpdateProjectRequest(String name) {
        this.name = name;
    }
}
