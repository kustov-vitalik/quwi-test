package com.example.quwitest.ui.projectlist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectsViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectsViewModel(context);
    }
}
