package com.example.quwitest.ui.editprojectdialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quwitest.data.local.Project;
import com.example.quwitest.databinding.FragmentEditProjectNameBinding;
import com.example.quwitest.ui.projectlist.ProjectsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditProjectNameFragment extends DialogFragment {
    private ProjectsViewModel viewModel;
    private FragmentEditProjectNameBinding binding;
    @Inject
    EditProjectFlowListener editProjectFlowListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectsViewModel.class);
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
        binding.buttonCancel.setOnClickListener(v -> editProjectFlowListener.onCancelEditing());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void onSaveProjectNameRequested(View view) {
        final String updatedName = binding.editTextProjectName.getText().toString().trim();
        if (updatedName.isEmpty()) {
            editProjectFlowListener.onEmptyInputData();
            return;
        }

        viewModel.saveProjectName(getProject(), updatedName,
                editProjectFlowListener::onEditSuccess,
                throwable -> editProjectFlowListener.onEditFailed(throwable.getLocalizedMessage())
        );
    }

    public interface EditProjectFlowListener {
        void onEmptyInputData();

        void onEditFailed(String error);

        void onCancelEditing();

        void onEditSuccess(Project project);
    }
}