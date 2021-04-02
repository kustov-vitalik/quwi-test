package com.example.quwitest.data.network;

import com.example.quwitest.data.network.dto.AuthRequest;
import com.example.quwitest.data.network.dto.AuthResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthResource {

    @POST("v2/auth/login")
    Single<AuthResponse> login(@Body AuthRequest request);

    @POST("v2/auth/logout")
    Completable logout();

    @GET("v2/forgot-password/check")
    Completable checkToken(@Query("token") String token);
}
