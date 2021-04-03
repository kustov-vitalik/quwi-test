package com.example.quwitest;

import android.preference.PreferenceManager;

import com.example.quwitest.data.LoginRepository;
import com.example.quwitest.data.ProjectsRepository;
import com.example.quwitest.data.local.sharedpreferences.TokenStorage;
import com.example.quwitest.data.network.RESTServicesProvider;
import com.example.quwitest.utils.CircleImageTransformation;
import com.squareup.picasso.Transformation;

public class Application extends android.app.Application {

    private TokenStorage tokenStorage;
    private LoginRepository loginRepository;
    private ProjectsRepository projectsRepository;
    private Transformation listImageTransformation;

    @Override
    public void onCreate() {
        super.onCreate();

        tokenStorage = new TokenStorage(PreferenceManager.getDefaultSharedPreferences(this));
        RESTServicesProvider restServicesProvider = new RESTServicesProvider(tokenStorage, getString(R.string.quwi_base_url));

        loginRepository = new LoginRepository(restServicesProvider.authResource(), restServicesProvider.usersResource());
        projectsRepository = new ProjectsRepository(restServicesProvider.projectsResource());

        int radius = getResources().getDimensionPixelSize(R.dimen.project_logo_size);
        int margin = getResources().getDimensionPixelSize(R.dimen.small_margin);
        listImageTransformation = new CircleImageTransformation(radius, margin);

    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    public ProjectsRepository getProjectsRepository() {
        return projectsRepository;
    }

    public Transformation getListImageTransformation() {
        return listImageTransformation;
    }
}
