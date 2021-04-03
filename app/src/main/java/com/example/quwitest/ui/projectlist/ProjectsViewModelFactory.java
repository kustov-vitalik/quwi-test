package com.example.quwitest.ui.projectlist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.quwitest.Application;

import java.util.Objects;

public class ProjectsViewModelFactory implements ViewModelProvider.Factory {

    private final Application context;

    public ProjectsViewModelFactory(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("Application context required");
        }
        this.context = (Application) context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ProjectsViewModel viewModel = new ProjectsViewModel(context.getProjectsRepository());
        try {
            return Objects.requireNonNull(modelClass.cast(viewModel));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
