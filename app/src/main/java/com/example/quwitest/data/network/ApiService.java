package com.example.quwitest.data.network;

import android.content.Context;

import com.example.quwitest.data.local.sharedpreferences.TokenStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static final String BASE_URL = "https://api.quwi.com/";

    private static Retrofit retrofit;
    private static Gson gson;
    private static OkHttpClient okHttpClient;
    private static AuthResource authResource;
    private static ProjectsResource projectsResource;
    private static UsersResource usersResource;

    public static Retrofit retrofit(Context context) {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson()))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okHttpClient(context))
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static OkHttpClient okHttpClient(Context context) {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    httpClient.addInterceptor(authInterceptor(context));
                    okHttpClient = httpClient.build();
                }
            }
        }
        return okHttpClient;
    }

    public static Gson gson() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new GsonBuilder()
                            .serializeNulls()
                            .create();
                }
            }
        }
        return gson;
    }

    private static Interceptor authInterceptor(Context context) {
        return chain -> {

            final Request.Builder builder = chain.request().newBuilder();

            final String language = Locale.getDefault().toLanguageTag();
            builder.addHeader("Client-Language", language);
            final String authToken = TokenStorage.getInstance(context).getToken();
            if (authToken != null) {
                builder.addHeader("Authorization", String.format("Bearer %s", authToken));
            }

            return chain.proceed(builder.build());
        };
    }

    public static AuthResource authResource(Context context) {
        if (authResource == null) {
            synchronized (AuthResource.class) {
                if (authResource == null) {
                    authResource = retrofit(context).create(AuthResource.class);
                }
            }
        }
        return authResource;
    }

    public static ProjectsResource projectsResource(Context context) {
        if (projectsResource == null) {
            synchronized (ProjectsResource.class) {
                if (projectsResource == null) {
                    projectsResource = retrofit(context).create(ProjectsResource.class);
                }
            }
        }
        return projectsResource;
    }

    public static UsersResource usersResource(Context context) {
        if (usersResource == null) {
            synchronized (UsersResource.class) {
                if (usersResource == null) {
                    usersResource = retrofit(context).create(UsersResource.class);
                }
            }
        }
        return usersResource;
    }
}
