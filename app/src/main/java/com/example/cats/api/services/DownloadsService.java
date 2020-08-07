package com.example.cats.api.services;

import com.example.cats.api.models.res.Downloads;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DownloadsService {
    @GET("images/search")
    Call<List<Downloads>> getAllDownloads();

}
