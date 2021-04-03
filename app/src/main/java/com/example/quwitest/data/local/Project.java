package com.example.quwitest.data.local;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Project implements Parcelable {
    public abstract int getId();

    public abstract String getName();

    @Nullable
    public abstract String getLogoUrl();

    public abstract boolean isActive();

    public static Builder builder() {
        return new AutoValue_Project.Builder();
    }

    public static Project fromDTO(com.example.quwitest.data.network.dto.Project dto) {
        return Project.builder()
                .id(dto.getId())
                .active(dto.getIsActive() == 1)
                .logoUrl(dto.getLogoUrl())
                .name(dto.getName())
                .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder logoUrl(@Nullable String logoUrl);

        public abstract Builder active(boolean active);

        public abstract Project build();
    }
}
