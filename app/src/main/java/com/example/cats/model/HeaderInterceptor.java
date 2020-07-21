package com.example.cats.model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("97a76886-9a72-4bb1-81a2-e730833ffbdb", "key")
                .build();
        return chain.proceed(request);
    }
}
