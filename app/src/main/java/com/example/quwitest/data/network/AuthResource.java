package com.example.quwitest.data.network;

import com.example.quwitest.data.network.dto.AuthRequest;
import com.example.quwitest.data.network.dto.AuthResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthResource {

    @POST("v2/auth/login")
    Single<AuthResponse> login(@Body AuthRequest request);

    @POST("v2/auth/logout")
    Completable logout();
}
