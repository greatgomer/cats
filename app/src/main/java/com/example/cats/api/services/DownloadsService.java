package com.example.cats.api.services;

import com.example.cats.api.models.req.LoadFromDownloads;
import com.example.cats.api.models.res.Downloads;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DownloadsService {
    @GET("images/search")
    Call<List<Downloads>> getAllDownloads();

    @Multipart
    @POST("images/upload")
    Call<LoadFromDownloads> loadImage(@Part MultipartBody.Part filePart, @Part ("sub_id") String sub_id);

}
