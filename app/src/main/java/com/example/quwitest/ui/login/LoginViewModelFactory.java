package com.example.quwitest.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.quwitest.Application;

import java.util.Objects;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Application context;

    public LoginViewModelFactory(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("Application context required");
        }
        this.context = (Application) context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        LoginViewModel viewModel = new LoginViewModel(context.getLoginRepository(), context.getTokenStorage());
        try {
            return Objects.requireNonNull(modelClass.cast(viewModel));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
