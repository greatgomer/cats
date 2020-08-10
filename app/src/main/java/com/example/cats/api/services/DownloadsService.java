package com.example.cats.api.services;

import com.example.cats.api.models.req.DeleteFromDownloads;
import com.example.cats.api.models.res.Downloads;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DownloadsService {
    @GET("images")
    Call<List<Downloads>> getAllDownloads(@Query("sub_id") String sub_id, @Query("limit") Integer limit);

    @Multipart
    @POST("images/upload")
    Call<ResponseBody> loadImage(@Part MultipartBody.Part file, @Part("sub_id") RequestBody sub_id);

    @DELETE("images/{image_id}")
    Call<DeleteFromDownloads> deleteFromDownloads(@Path("image_id") String body);

}
