package com.example.quwitest.ui.editprojectdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quwitest.data.network.dto.Project;
import com.example.quwitest.databinding.FragmentEditProjectNameBinding;
import com.example.quwitest.ui.projectlist.ProjectsViewModel;
import com.example.quwitest.ui.projectlist.ProjectsViewModelFactory;

import org.jetbrains.annotations.NotNull;

public class EditProjectNameFragment extends DialogFragment {
    private ProjectsViewModel viewModel;
    private FragmentEditProjectNameBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new ProjectsViewModelFactory(getContext())).get(ProjectsViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProjectNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Project project = (Project) getArguments().getSerializable("project");
        binding.editTextProjectName.setText(project.getName());
        binding.buttonSave.setOnClickListener(v -> onSaveProjectName(project));
        binding.buttonCancel.setOnClickListener(v -> goBackToDetails());
    }

    private void onSaveProjectName(Project project) {
        final String updatedName = binding.editTextProjectName.getText().toString().trim();
        if (updatedName.isEmpty()) {
            Toast.makeText(requireActivity(), "Empty name", Toast.LENGTH_LONG).show();
            return;
        }
        viewModel.saveProjectName(project, updatedName, updatedProject -> goBackToDetails(), throwable -> {
            Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private void goBackToDetails() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}