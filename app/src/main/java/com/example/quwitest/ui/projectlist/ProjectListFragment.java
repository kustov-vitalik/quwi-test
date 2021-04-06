package com.example.quwitest.ui.projectlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quwitest.data.local.Project;
import com.example.quwitest.databinding.FragmentProjectsListBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProjectListFragment extends Fragment {

    private ProjectsViewModel viewModel;
    private FragmentProjectsListBinding binding;

    @Inject
    ProjectListRecyclerViewAdapter adapter;

    @Inject
    ProjectListFlowListener projectListFlow;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectsViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshProjects();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeContainer.setOnRefreshListener(this::refreshProjects);
        binding.swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        adapter.setOnProjectClickListener(projectListFlow::openDetails);
        binding.list.setAdapter(adapter);

        viewModel.getProjects().observe(getViewLifecycleOwner(), items -> {
            if (items == null) {
                return;
            }
            adapter.setItems(items);
        });
        viewModel.getLoadingInProgress().observe(getViewLifecycleOwner(), this::switchLoadingIndicator);
        viewModel.getError().observe(getViewLifecycleOwner(), projectListFlow::showError);


    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void refreshProjects() {
        viewModel.loadProjects();
    }

    private void switchLoadingIndicator(Boolean loading) {
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.swipeContainer.setRefreshing(false);
        }
    }

    public interface ProjectListFlowListener {
        void openDetails(Project project);

        void showError(Throwable error);
    }

}