package com.example.cats.ui.home.fragments.cats;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.FavouritesService;
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

public class CatsFragmentViewModel extends AndroidViewModel {
    @Inject
    ImagesService service;

    @Inject
    FavouritesService serviceFavourites;

    @SuppressLint("StaticFieldLeak")
    Context context;

    public static HashMap<String, String> parameters = new HashMap<>();
    public static String email = "";
    private CatRecyclerAdapter adapter;
    public List<Cat> resultCats = new ArrayList<>();
    FragmentCatsBinding binding;

    public CatsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void catsViewModel(FragmentCatsBinding binding) {
        this.binding = binding;
        ((MyApplication) getApplication().getApplicationContext()).appComponent.inject(this);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.favourite(this);
        context = getApplication();
    }

//        private void loadCats() {
//        service.getAllData(parameters)
//                .subscribe(this::generateDataList,
//                        e -> Log.d("RETROFIT", String.valueOf(e)))
//                .isDisposed();
//    }

    public void loadCats() {
        service.getAllCats(parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        generateDataList(response.body());
                    }
                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void generateDataList(List<Cat> photoList) {
        if (resultCats.isEmpty()) {
            resultCats = new ArrayList<>(photoList);
            adapter = new CatRecyclerAdapter(serviceFavourites, context, resultCats);
            binding.catRecyclerView.setAdapter(adapter);
        } else {
            resultCats.addAll(photoList);
            adapter.notifyDataSetChanged();
        }
    }

}
