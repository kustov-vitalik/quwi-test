package com.example.quwitest.ui.projectlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quwitest.data.network.dto.Project;
import com.example.quwitest.databinding.FragmentProjectsListBinding;

import org.jetbrains.annotations.NotNull;

public class ProjectListFragment extends Fragment implements ProjectListRecyclerViewAdapter.OnProjectClickListener {

    private ProjectsViewModel viewModel;
    private ProjectListRecyclerViewAdapter adapter;
    private FragmentProjectsListBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeContainer.setOnRefreshListener(() -> viewModel.loadProjects());
        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        adapter = new ProjectListRecyclerViewAdapter();
        adapter.setOnProjectClickListener(this);
        binding.list.setAdapter(adapter);


        viewModel = new ViewModelProvider(requireActivity(), new ProjectsViewModelFactory(getContext())).get(ProjectsViewModel.class);

        viewModel.getProjects().observe(getViewLifecycleOwner(), items -> adapter.setItems(items));
        viewModel.getLoadingInProgress().observe(getViewLifecycleOwner(), this::switchProgressBar);
        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showError(Throwable throwable) {
        Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        Log.i(getTag(), throwable.getMessage(), throwable);
    }

    private void switchProgressBar(Boolean showProgress) {
        if (showProgress) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void onProjectClick(Project project) {
        NavHostFragment.findNavController(this).navigate(ProjectListFragmentDirections.actionProjectListFragmentToDetailsFragment());
        viewModel.setCurrentProject(project);
    }

}