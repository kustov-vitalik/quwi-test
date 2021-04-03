package com.example.quwitest.data.network;

import com.example.quwitest.data.local.sharedpreferences.TokenStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTServicesProvider {
    private static final String BASE_URL = "https://api.quwi.com/";

    private final TokenStorage tokenStorage;
    private final String apiBaseUrl;

    public RESTServicesProvider(TokenStorage tokenStorage, String apiBaseUrl) {
        this.tokenStorage = tokenStorage;
        this.apiBaseUrl = apiBaseUrl == null ? BASE_URL : apiBaseUrl;
    }

    // singleton - just to simplify
    private Retrofit retrofit;

    private Retrofit retrofit() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(apiBaseUrl)
                            .addConverterFactory(GsonConverterFactory.create(gson()))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private OkHttpClient okHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(authAndLangInterceptor());
        return httpClient.build();
    }

    private Gson gson() {
        return new GsonBuilder()
                .serializeNulls()
                .create();
    }

    private Interceptor authAndLangInterceptor() {
        return chain -> {

            final Request.Builder builder = chain.request().newBuilder();

            String language = Locale.getDefault().toLanguageTag();
            final List<String> supportedLocales = Collections.unmodifiableList(Arrays.asList("ru-RU", "en-US"));
            if (!supportedLocales.contains(language)) {
                language = "en-US";
            }

            builder.addHeader("Client-Language", language);
            final String authToken = tokenStorage.getToken();
            if (authToken != null) {
                builder.addHeader("Authorization", String.format("Bearer %s", authToken));
            }

            return chain.proceed(builder.build());
        };
    }

    public AuthResource authResource() {
        return retrofit().create(AuthResource.class);
    }

    public ProjectsResource projectsResource() {
        return retrofit().create(ProjectsResource.class);
    }

    public UsersResource usersResource() {
        return retrofit().create(UsersResource.class);
    }
}
