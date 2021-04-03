package com.example.quwitest.ui.loading;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quwitest.MainActivity;
import com.example.quwitest.databinding.FragmentLoadingBinding;
import com.example.quwitest.ui.login.LoginViewModel;
import com.example.quwitest.ui.login.LoginViewModelFactory;

import org.jetbrains.annotations.NotNull;

public class LoadingFragment extends Fragment {
    private FragmentLoadingBinding binding;
    private LoginViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory(context.getApplicationContext())).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle state) {
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle state) {
        super.onViewCreated(view, state);
        viewModel.getLoggedIn().observe(getViewLifecycleOwner(), loggedIn -> {
            MainActivity activity = (MainActivity) requireActivity();
            if (loggedIn) {
                activity.navigate(LoadingFragmentDirections.actionLoadingFragmentToProjectListFragment());
            } else {
                activity.navigate(LoadingFragmentDirections.actionLoadingFragmentToLoginFragment());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}