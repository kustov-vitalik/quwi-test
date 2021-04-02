package com.example.quwitest.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(context);
    }
}
