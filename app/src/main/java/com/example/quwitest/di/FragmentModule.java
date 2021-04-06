package com.example.quwitest.di;

import com.example.quwitest.ui.editprojectdialog.EditProjectFlowListener;
import com.example.quwitest.ui.editprojectdialog.EditProjectNameFragment;
import com.example.quwitest.ui.loading.LoadingFlowListener;
import com.example.quwitest.ui.loading.LoadingFragment;
import com.example.quwitest.ui.login.LoginFlowListener;
import com.example.quwitest.ui.login.LoginFragment;
import com.example.quwitest.ui.projectdetails.DetailsFlowListener;
import com.example.quwitest.ui.projectdetails.DetailsFragment;
import com.example.quwitest.ui.projectlist.ProjectListFlowListener;
import com.example.quwitest.ui.projectlist.ProjectListFragment;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public abstract class FragmentModule {

    @Binds
    public abstract LoginFragment.LoginFlowListener loginFlowListener(LoginFlowListener loginFlowListener);

    @Binds
    public abstract EditProjectNameFragment.EditProjectFlowListener editProjectFlowListener(EditProjectFlowListener editProjectFlowListener);

    @Binds
    public abstract LoadingFragment.LoadingFlowListener loadingFlowListener(LoadingFlowListener loadingFlowListener);

    @Binds
    public abstract DetailsFragment.DetailsFlowListener detailsFlowListener(DetailsFlowListener detailsFlowListener);

    @Binds
    public abstract ProjectListFragment.ProjectListFlowListener projectListFlowListener(ProjectListFlowListener projectListFlowListener);
}
