package com.example.quwitest.ui.projectlist;

import android.content.Context;
import android.widget.Toast;

import com.example.quwitest.MainActivity;
import com.example.quwitest.data.local.Project;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class ProjectListFlowListener implements ProjectListFragment.ProjectListFlowListener {

    private final Context context;

    @Inject
    public ProjectListFlowListener(@NotNull @ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void openDetails(Project project) {
        ((MainActivity) context).navigate(ProjectListFragmentDirections.actionProjectListFragmentToDetailsFragment(project));
    }

    @Override
    public void showError(Throwable error) {
        Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
