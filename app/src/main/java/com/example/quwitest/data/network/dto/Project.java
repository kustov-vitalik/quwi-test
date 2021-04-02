package com.example.quwitest.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {
    Integer id;
    String name;
    @SerializedName("logo_url")
    String logoUrl;
    @SerializedName("is_active")
    Integer isActive;
}
