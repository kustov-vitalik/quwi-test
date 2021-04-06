package com.example.quwitest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, new AppBarConfiguration.Builder(R.id.projectListFragment, R.id.loginFragment, R.id.loadingFragment)
                .setFallbackOnNavigateUpListener(this::onSupportNavigateUp)
                .build()
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        navController = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        navigateUp();
    }

    public void navigate(NavDirections directions) {
        navController.navigate(directions);
    }

    public void navigateUp() {
        if (!navController.popBackStack()) {
            finish();
        }
    }
}