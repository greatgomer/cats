package com.example.cats.api.services;

import com.example.cats.api.interseptors.HeaderInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ProviderCat {
    private static Retrofit retrofit;

    public ProviderCat(){
        retrofit = createProvider();
    }

    private Retrofit createProvider() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new HeaderInterceptor());

        retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl("https://api.thecatapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    @Singleton
    @Provides
    public ImagesService createImagesService() {
        return retrofit.create(ImagesService.class);
    }

}