package com.example.cats.api.services;

import com.example.cats.api.models.res.Downloads;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DownloadsService {
    @GET("images/search")
    Call<List<Downloads>> getAllDownloads();

    @Multipart
    @Headers("Content-Type: multipart/form-data;")
    @POST("images/upload")
    Call<ResponseBody> loadImage(@Part MultipartBody.Part image, @Part("sub_id") RequestBody sub_id);

}
