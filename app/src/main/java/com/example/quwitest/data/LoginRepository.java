package com.example.quwitest.data;

import android.content.Context;

import com.example.quwitest.data.network.ApiService;
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

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private AuthResource authResource;
    private UsersResource usersResource;

    private LoginRepository(Context context) {
        authResource = ApiService.authResource(context);
        usersResource = ApiService.usersResource(context);
    }

    public static LoginRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (LoginRepository.class) {
                if (instance == null) {
                    instance = new LoginRepository(context);
                }
            }
        }
        return instance;
    }

    public Completable logout() {
        return authResource.logout();
    }


    public Single<AuthResponse> login(String username, String password) {
        // handle login
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