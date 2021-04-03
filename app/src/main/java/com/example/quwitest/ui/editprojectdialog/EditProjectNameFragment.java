package com.example.quwitest.ui.editprojectdialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quwitest.MainActivity;
import com.example.quwitest.R;
import com.example.quwitest.data.local.Project;
import com.example.quwitest.databinding.FragmentEditProjectNameBinding;
import com.example.quwitest.ui.projectlist.ProjectsViewModel;
import com.example.quwitest.ui.projectlist.ProjectsViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EditProjectNameFragment extends DialogFragment {
    private ProjectsViewModel viewModel;
    private FragmentEditProjectNameBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity(), new ProjectsViewModelFactory(context.getApplicationContext())).get(ProjectsViewModel.class);
    }

    @NonNull
    private Project getProject() {
        Project project = EditProjectNameFragmentArgs.fromBundle(requireArguments()).getProject();
        return Objects.requireNonNull(project);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProjectNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Project project = getProject();
        binding.editTextProjectName.setText(project.getName());
        binding.buttonSave.setOnClickListener(this::onSaveProjectNameRequested);
        binding.buttonCancel.setOnClickListener(v -> goBack());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void onSaveProjectNameRequested(View view) {
        final String updatedName = binding.editTextProjectName.getText().toString().trim();
        if (updatedName.isEmpty()) {
            showError(getString(R.string.empty_project_name));
            return;
        }

        viewModel.saveProjectName(getProject(), updatedName, this::onProjectUpdatedSuccessfully, throwable -> showError(throwable.getLocalizedMessage()));
    }

    private void goBack() {
        ((MainActivity) requireActivity()).navigateUp();
    }

    private void showError(String error) {
        Toast.makeText(getContext().getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    private void onProjectUpdatedSuccessfully(Project project) {
        MainActivity activity = (MainActivity) requireActivity();
        activity.navigate(EditProjectNameFragmentDirections.actionEditProjectNameFragmentToDetailsFragment(project));
    }
}