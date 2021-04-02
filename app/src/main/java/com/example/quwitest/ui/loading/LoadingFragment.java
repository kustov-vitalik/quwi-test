package com.example.quwitest.ui.loading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.quwitest.R;
import com.example.quwitest.databinding.FragmentLoadingBinding;
import com.example.quwitest.ui.login.LoginViewModel;
import com.example.quwitest.ui.login.LoginViewModelFactory;

import org.jetbrains.annotations.NotNull;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoadingFragment extends Fragment {
    private FragmentLoadingBinding binding;
    private LoginViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory(getContext())).get(LoginViewModel.class);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getLoggedIn().observe(getViewLifecycleOwner(), loggedIn -> {
            final NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            if (loggedIn) {
                navController.navigate(LoadingFragmentDirections.actionLoadingFragmentToProjectListFragment());
            } else {
                navController.navigate(LoadingFragmentDirections.actionLoadingFragmentToLoginFragment());
            }
            binding.loadingProgressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}