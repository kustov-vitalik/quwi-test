package com.example.quwitest.ui.projectlist;

import android.content.Context;
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

import com.example.quwitest.Application;
import com.example.quwitest.MainActivity;
import com.example.quwitest.data.local.Project;
import com.example.quwitest.databinding.FragmentProjectsListBinding;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;

public class ProjectListFragment extends Fragment {

    private ProjectsViewModel viewModel;
    private ProjectListRecyclerViewAdapter adapter;
    private FragmentProjectsListBinding binding;
    private Transformation transformation;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity(), new ProjectsViewModelFactory(context.getApplicationContext())).get(ProjectsViewModel.class);
        transformation = ((Application) context.getApplicationContext()).getListImageTransformation();
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

        adapter = new ProjectListRecyclerViewAdapter(this::onProjectClick, transformation);
        binding.list.setAdapter(adapter);

        viewModel.getProjects().observe(getViewLifecycleOwner(), items -> {
            if (items == null) {
                return;
            }
            adapter.setItems(items);
        });
        viewModel.getLoadingInProgress().observe(getViewLifecycleOwner(), this::switchLoadingIndicator);
        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);


    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void refreshProjects() {
        viewModel.loadProjects();
    }

    private void showError(Throwable throwable) {
        Toast.makeText(requireContext().getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        Log.e(getTag(), throwable.getMessage(), throwable);
    }

    private void switchLoadingIndicator(Boolean loading) {
        if (loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.swipeContainer.setRefreshing(false);
        }
    }

    private void onProjectClick(Project project) {
        MainActivity activity = (MainActivity) requireActivity();
        activity.navigate(ProjectListFragmentDirections.actionProjectListFragmentToDetailsFragment(project));
    }

}