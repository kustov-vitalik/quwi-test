package com.example.quwitest.ui.login;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.example.quwitest.MainActivity;
import com.example.quwitest.R;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class LoginFlowListener implements LoginFragment.LoginFlowListener {

    private final Context context;

    @Inject
    public LoginFlowListener(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void onLoginSuccess(@NotNull LoggedInUserView model) {
        String welcome = context.getString(R.string.welcome) + " " + model.getDisplayName();
        Toast.makeText(context, welcome, Toast.LENGTH_LONG).show();

        MainActivity activity = (MainActivity) context;
        activity.navigate(LoginFragmentDirections.actionLoginFragmentToProjectListFragment());
    }

    @Override
    public void onLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(context, errorString, Toast.LENGTH_LONG).show();
    }
}
