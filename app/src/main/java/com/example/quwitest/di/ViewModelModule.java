package com.example.quwitest.di;

import com.example.quwitest.data.network.AuthResource;
import com.example.quwitest.data.network.ProjectsResource;
import com.example.quwitest.data.network.UsersResource;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import retrofit2.Retrofit;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class ViewModelModule {
    @Provides
    @ViewModelScoped
    public static AuthResource authResource(@NotNull Retrofit retrofit) {
        return retrofit.create(AuthResource.class);
    }

    @Provides
    @ViewModelScoped
    public static ProjectsResource projectsResource(@NotNull Retrofit retrofit) {
        return retrofit.create(ProjectsResource.class);
    }

    @Provides
    @ViewModelScoped
    public static UsersResource usersResource(@NotNull Retrofit retrofit) {
        return retrofit.create(UsersResource.class);
    }

}
