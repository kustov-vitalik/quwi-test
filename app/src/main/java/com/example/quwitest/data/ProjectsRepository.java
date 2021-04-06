package com.example.quwitest.data;

import com.example.quwitest.data.local.Project;
import com.example.quwitest.data.network.ProjectsResource;
import com.example.quwitest.data.network.dto.ProjectListResponse;
import com.example.quwitest.data.network.dto.UpdateProjectRequest;
import com.example.quwitest.data.network.dto.UpdateProjectResponse;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsRepository {

    private final ProjectsResource projectsResource;

    @Inject
    public ProjectsRepository(ProjectsResource projectsResource) {
        this.projectsResource = projectsResource;
    }

    public Observable<List<Project>> getProjects() {
        return projectsResource.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ProjectListResponse::getProjects)
                .map(projects -> projects.stream().map(Project::fromDTO).collect(Collectors.toList()));
    }

    public Single<Project> updateProjectName(Project project, String updatedName) {
        return projectsResource.update(project.getId(), new UpdateProjectRequest(updatedName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(UpdateProjectResponse::getProject)
                .map(Project::fromDTO);
    }


}
