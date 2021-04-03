package com.example.quwitest.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectListResponse {
    @SerializedName("projects")
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }
}
