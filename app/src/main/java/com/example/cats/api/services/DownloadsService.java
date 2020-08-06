package com.example.cats.api.services;

import com.example.cats.api.models.res.Downloads;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DownloadsService {
    @GET("images/search")
    Call<List<Downloads>> getAllDownloads();

    @Headers("content-Type: multipart/form-data;")
    @POST("images/upload")
    Call<ResponseBody> loadImage(@Body RequestBody filePart);

}
