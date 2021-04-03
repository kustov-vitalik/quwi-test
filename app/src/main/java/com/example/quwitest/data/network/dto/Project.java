package com.example.quwitest.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class Project {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo_url")
    private String logoUrl;
    @SerializedName("is_active")
    private Integer isActive;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Integer getIsActive() {
        return isActive;
    }
}
