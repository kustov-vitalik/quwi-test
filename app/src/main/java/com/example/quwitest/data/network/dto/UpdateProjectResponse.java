package com.example.quwitest.data.network.dto;


import com.google.gson.annotations.SerializedName;

public class UpdateProjectResponse {
    @SerializedName("project")
    private Project project;

    public Project getProject() {
        return project;
    }
}
