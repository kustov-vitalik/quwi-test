package com.example.quwitest.data.network;

import com.example.quwitest.data.network.dto.CurrentUserResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UsersResource {
    @GET("v2/users/profile")
    Observable<CurrentUserResponse> currentUserProfile();
}
