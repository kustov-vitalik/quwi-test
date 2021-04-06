package com.example.quwitest.ui.loading;

import android.content.Context;

import com.example.quwitest.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class LoadingFlowListener implements LoadingFragment.LoadingFlowListener {

    private final Context context;

    @Inject
    public LoadingFlowListener(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void onUserLoggedIn() {
        ((MainActivity) context).navigate(LoadingFragmentDirections.actionLoadingFragmentToProjectListFragment());
    }

    @Override
    public void onAuthenticationRequired() {
        ((MainActivity) context).navigate(LoadingFragmentDirections.actionLoadingFragmentToLoginFragment());
    }
}
