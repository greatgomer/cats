package com.example.cats.model;

import com.example.cats.activities.ImageDetails;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkService {
    private static Retrofit retrofit;

    public NetworkService(){
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
    public JSONPlaceHolderApi createImagesService(){
        return retrofit.create(JSONPlaceHolderApi.class);
    }
}
//
//    private static final String BASE_URL = "https://api.thecatapi.com/v1/";
//    public static Retrofit getInstance() {
//        if (retrofit == null) {
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//            httpClient.addNetworkInterceptor(new HeaderInterceptor());
//
//            retrofit = new retrofit2.Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
//                    .build();
//        }
//        return retrofit;
//    }
//}