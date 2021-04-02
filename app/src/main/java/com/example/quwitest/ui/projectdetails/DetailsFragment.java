package com.example.quwitest.ui.projectdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quwitest.R;
import com.example.quwitest.data.network.dto.Project;
import com.example.quwitest.databinding.DetailsFragmentBinding;
import com.example.quwitest.ui.projectlist.ProjectsViewModel;
import com.example.quwitest.ui.projectlist.ProjectsViewModelFactory;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {

    private ProjectsViewModel viewModel;
    private DetailsFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new ProjectsViewModelFactory(getContext())).get(ProjectsViewModel.class);
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
        viewModel.getCurrentProject().observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(Project project) {
        binding.projectActive.setText(project.getIsActive() == 1 ? "Yes" : "No");
        binding.projectName.setText(project.getName());
        Picasso.get().load(project.getLogoUrl())
                .placeholder(R.drawable.project_logo_placeholder)
                .error(R.drawable.project_logo_placeholder)
                .into(binding.projectLogo);
        binding.editNameButton.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putSerializable("project", project);
            NavHostFragment.findNavController(this).navigate(DetailsFragmentDirections.actionDetailsFragmentToEditProjectNameFragment().getActionId(), params);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}