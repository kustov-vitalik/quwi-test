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

import com.example.quwitest.databinding.FragmentLoadingBinding;
import com.example.quwitest.ui.login.LoginViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoadingFragment extends Fragment {
    private FragmentLoadingBinding binding;

    private LoginViewModel viewModel;

    @Inject
    LoadingFlowListener loadingFlowListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
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
            if (loggedIn) {
                loadingFlowListener.onUserLoggedIn();
            } else {
                loadingFlowListener.onAuthenticationRequired();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface LoadingFlowListener {
        void onUserLoggedIn();

        void onAuthenticationRequired();
    }
}