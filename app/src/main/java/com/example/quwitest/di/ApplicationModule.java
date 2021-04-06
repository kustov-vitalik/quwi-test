package com.example.quwitest.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.quwitest.R;
import com.example.quwitest.data.local.sharedpreferences.TokenStorage;
import com.example.quwitest.utils.CircleImageTransformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ApplicationModule {

    @Provides
    @Singleton
    public static TokenStorage tokenStorage(@NotNull @ApplicationContext Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new TokenStorage(sharedPreferences);
    }

    @Provides
    @Singleton
    public static Transformation imageCircleTransformation(@NotNull @ApplicationContext Context context) {
        int radius = context.getResources().getDimensionPixelSize(R.dimen.project_logo_size);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.small_margin);
        return new CircleImageTransformation(radius, margin);
    }

    @Provides
    @Singleton
    public static Retrofit retrofit(@NotNull @ApplicationContext Context context, @NotNull OkHttpClient okHttpClient, @NotNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.quwi_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public static Gson gson() {
        return new GsonBuilder()
                .serializeNulls()
                .create();
    }

    @Provides
    @Singleton
    public static OkHttpClient okHttpClient(@NotNull Interceptor authAndLandInterceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(authAndLandInterceptor);
        return httpClient.build();
    }

    @Provides
    @Singleton
    public static Interceptor authAndLangInterceptor(@NotNull TokenStorage tokenStorage) {
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
}
