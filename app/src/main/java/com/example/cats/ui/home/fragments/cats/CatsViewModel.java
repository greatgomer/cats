package com.example.cats.ui.home.fragments.cats;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;
import com.example.cats.databinding.FragmentCatsBinding;
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

    @SuppressLint("StaticFieldLeak")
    Context context;
    LinearLayoutManager mLayoutManager;

    FragmentCatsBinding binding;
    public static HashMap<String, String> parameters = new HashMap<>();
    private CatsAdapter adapter;
    public List<Cat> resultCats = new ArrayList<>();

    public CatsViewModel(@NonNull Application application) {
        super(application);
    }

    public void onCatsViewModel(FragmentCatsBinding binding) {
        this.binding = binding;
        context = getApplication();
        mLayoutManager = new GridLayoutManager(context, 2);
        binding.catRecyclerView.setLayoutManager(mLayoutManager);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.inject(this);
        loadCats();
    }

    public void loadCats() {
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
        resultCats.addAll(photoList);
        adapter = new CatsAdapter(context, resultCats);
        binding.catRecyclerView.setAdapter(adapter);
    }

}
