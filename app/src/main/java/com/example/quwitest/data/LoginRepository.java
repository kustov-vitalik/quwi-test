package com.example.quwitest.data;

import com.example.quwitest.data.network.AuthResource;
import com.example.quwitest.data.network.UsersResource;
import com.example.quwitest.data.network.dto.AuthRequest;
import com.example.quwitest.data.network.dto.AuthResponse;
import com.example.quwitest.data.network.dto.CurrentUserResponse;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginRepository {
    private final AuthResource authResource;
    private final UsersResource usersResource;

    public LoginRepository(AuthResource authResource, UsersResource usersResource) {
        this.authResource = authResource;
        this.usersResource = usersResource;
    }


    public Completable logout() {
        return authResource.logout();
    }


    public Single<AuthResponse> login(String username, String password) {
        return authResource.login(new AuthRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CurrentUserResponse.UserInfo> getCurrentUser() {
        return usersResource.currentUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(CurrentUserResponse::getUser);
    }
}