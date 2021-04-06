package com.example.quwitest.ui.editprojectdialog;

import android.content.Context;
import android.widget.Toast;

import com.example.quwitest.MainActivity;
import com.example.quwitest.R;
import com.example.quwitest.data.local.Project;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class EditProjectFlowListener implements EditProjectNameFragment.EditProjectFlowListener {

    private final Context context;

    @Inject
    public EditProjectFlowListener(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void onEmptyInputData() {
        showError(context.getString(R.string.empty_project_name));
    }

    @Override
    public void onEditFailed(String error) {
        showError(error);
    }

    @Override
    public void onCancelEditing() {
        MainActivity activity = (MainActivity) context;
        activity.navigateUp();
    }

    @Override
    public void onEditSuccess(Project project) {
        MainActivity activity = (MainActivity) context;
        activity.navigate(EditProjectNameFragmentDirections.actionEditProjectNameFragmentToDetailsFragment(project));
    }

    private void showError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
