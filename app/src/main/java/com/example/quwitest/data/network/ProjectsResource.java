package com.example.quwitest.data.network;

import com.example.quwitest.data.network.dto.ProjectListResponse;
import com.example.quwitest.data.network.dto.UpdateProjectRequest;
import com.example.quwitest.data.network.dto.UpdateProjectResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProjectsResource {

    @GET("v2/projects-manage/index")
    Observable<ProjectListResponse> list();

    @POST("v2/projects-manage/update")
    Single<UpdateProjectResponse> update(@Query("id") Integer id, @Body UpdateProjectRequest request);
}
