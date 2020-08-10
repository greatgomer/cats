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
public class NetworkProvider {
    private static Retrofit retrofit;

    public NetworkProvider(){
        retrofit = createProvider();
    }

    private Retrofit createProvider() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new HeaderInterceptor());

//        LOGS
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.addInterceptor(new HeaderInterceptor());// add your other interceptors …
//        client.addInterceptor(logging);  // <-- this is the important line!

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

    @Singleton
    @Provides
    public  FavouritesService createFavouritesService() {

        return retrofit.create(FavouritesService.class);
    }

    @Singleton
    @Provides
    public  DownloadsService createDownloadsService() {

        return retrofit.create(DownloadsService.class);
    }

    @Singleton
    @Provides
    public  ImageService createImageService() {

        return retrofit.create(ImageService.class);
    }

}