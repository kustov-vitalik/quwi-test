package com.example.quwitest.data;

import android.content.Context;

import com.example.quwitest.data.network.ApiService;
import com.example.quwitest.data.network.ProjectsResource;
import com.example.quwitest.data.network.dto.Project;
import com.example.quwitest.data.network.dto.ProjectListResponse;
import com.example.quwitest.data.network.dto.UpdateProjectRequest;
import com.example.quwitest.data.network.dto.UpdateProjectResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsRepository {

    private final ProjectsResource projectsResource;

    public ProjectsRepository(Context context) {
        projectsResource = ApiService.projectsResource(context);
    }

    public Observable<List<Project>> getProjects() {
        return projectsResource.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ProjectListResponse::getProjects);
    }

    public Single<Project> updateProjectName(Project project, String updatedName) {
        return projectsResource.update(project.getId(), new UpdateProjectRequest(updatedName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(UpdateProjectResponse::getProject);
    }


}
