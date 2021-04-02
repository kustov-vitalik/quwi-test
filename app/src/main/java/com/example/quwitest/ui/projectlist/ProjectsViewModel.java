package com.example.quwitest.ui.projectlist;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quwitest.data.ProjectsRepository;
import com.example.quwitest.data.network.dto.Project;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ProjectsViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<Project>> projects;
    private final MutableLiveData<Project> currentProject = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingInProgress = new MutableLiveData<>(false);
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();

    private final ProjectsRepository repository;

    public ProjectsViewModel(Context context) {
        repository = new ProjectsRepository(context);
    }

    public LiveData<Boolean> getLoadingInProgress() {
        return loadingInProgress;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public LiveData<List<Project>> getProjects() {
        if (projects == null) {
            projects = new MutableLiveData<>();
            loadProjects();
        }
        return projects;
    }

    public LiveData<Project> getCurrentProject() {
        return currentProject;
    }

    public void loadProjects() {
        loadingInProgress.setValue(true);
        disposable.add(
                repository.getProjects()
                        .subscribe(s -> {
                            projects.setValue(s);
                            loadingInProgress.setValue(false);
                        }, throwable -> {
                            error.setValue(throwable);
                            loadingInProgress.setValue(false);
                            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void saveProjectName(Project project, String updatedName, OnProjectUpdateSuccess successCallback, OnProjectUpdateFail failCallback) {
        disposable.add(
                repository.updateProjectName(project, updatedName)
                        .subscribe(projectUpdated -> {
                                    currentProject.setValue(projectUpdated);
                                    loadProjects();
                                    if (successCallback != null) {
                                        successCallback.execute(projectUpdated);
                                    }
                                },
                                throwable -> {
                                    error.setValue(throwable);
                                    if (failCallback != null) {
                                        failCallback.execute(throwable);
                                    }
                                    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
                                })
        );
    }

    public void setCurrentProject(Project project) {
        currentProject.setValue(project);
    }

    public interface OnProjectUpdateSuccess {
        void execute(Project project);
    }

    public interface OnProjectUpdateFail {
        void execute(Throwable throwable);
    }
}
