package com.example.quwitest.ui.projectdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quwitest.R;
import com.example.quwitest.data.local.Project;
import com.example.quwitest.databinding.DetailsFragmentBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {
    private DetailsFragmentBinding binding;

    @Inject
    DetailsFlowListener detailsFlow;

    @NonNull
    private Project getProject() {
        Project project = DetailsFragmentArgs.fromBundle(requireArguments()).getProject();
        return Objects.requireNonNull(project);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Project project = getProject();
        binding.editNameButton.setOnClickListener(v -> detailsFlow.onProjectEditDialogRequested(project));
        binding.projectActive.setText(project.isActive() ? getString(R.string.yes) : getString(R.string.no));
        binding.projectName.setText(project.getName());
        Picasso.get().load(project.getLogoUrl())
                .placeholder(R.drawable.project_logo_placeholder)
                .error(R.drawable.project_logo_placeholder)
                .into(binding.projectLogo);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public interface DetailsFlowListener {
        void onProjectEditDialogRequested(Project project);
    }
}