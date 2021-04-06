package com.example.quwitest.ui.projectdetails;

import android.content.Context;

import com.example.quwitest.MainActivity;
import com.example.quwitest.data.local.Project;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class DetailsFlowListener implements DetailsFragment.DetailsFlowListener {

    private final Context context;

    @Inject
    public DetailsFlowListener(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void onProjectEditDialogRequested(Project project) {
        ((MainActivity) context).navigate(DetailsFragmentDirections.actionDetailsFragmentToEditProjectNameFragment(project));
    }
}
