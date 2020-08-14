package com.example.cats.ui.home.fragments.cats;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatDataSource extends PageKeyedDataSource<Integer, Cat> {
    public static HashMap<String, String> parameters = new HashMap<>();
    private final int FIRST_PAGE = 1;
    ImagesService service;

    public CatDataSource(ImagesService service) {
        this.service = service;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Cat> callback) {
        parameters.put("limit", "20");
        service.getAllCats(parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        assert response.body() != null;
                        callback.onResult(response.body(), null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Cat> callback) {
        parameters.put("limit", "20");
        service.getAllCats(parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        assert response.body() != null;
                        callback.onResult(response.body(), adjacentKey);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Cat> callback) {
        parameters.put("limit", "20");
        service.getAllCats(parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        assert response.body() != null;
                        callback.onResult(response.body(), params.key + 1);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                    }
                });
    }

}
