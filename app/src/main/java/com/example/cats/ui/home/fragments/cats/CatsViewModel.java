package com.example.cats.ui.home.fragments.cats;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatsViewModel extends AndroidViewModel {
    @Inject
    ImagesService service;

    public static HashMap<String, String> parameters = new HashMap<>();
    public List<Cat> resultCats = new ArrayList<>();

    public CatsViewModel(@NonNull Application application) {
        super(application);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.inject(this);
        parameters.put("limit", "20");
        service.getAllCats(parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        generateDataList(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                    }
                });
    }

    public void generateDataList(List<Cat> photoList) {
        if (resultCats.isEmpty()) {
            resultCats = new ArrayList<>(photoList);
        } else {
            resultCats.addAll(photoList);
        }
        Log.d("TAGG", String.valueOf(resultCats.size()));
    }

}
