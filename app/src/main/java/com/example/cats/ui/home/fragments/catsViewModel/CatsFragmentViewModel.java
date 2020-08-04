package com.example.cats.ui.home.fragments.catsViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    LinearLayoutManager mLayoutManager;
    public int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public static HashMap<String, String> parameters = new HashMap<>();

    static SharedPreferences sharedPreferences;
    public static String email = "";

    public List<Cat> resultCats = new ArrayList<>();
    private CatRecyclerAdapter adapter;

    FragmentCatsBinding binding;

    public CatsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void catsViewModel(FragmentCatsBinding binding){
        this.binding = binding;
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        ((MyApplication) getApplication().getApplicationContext()).appComponent.inject(this);
        email = sharedPreferences.getString("email", "default value");
        context = getApplication();
        addMoreCatsInFragment();
    }

    public void addMoreCatsInFragment() {
        mLayoutManager = new GridLayoutManager(context, 2);
        binding.catRecyclerView.setLayoutManager(mLayoutManager);
        binding.catRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    loadCats();
                    loading = true;
                }
            }
        });
        loadCats();

    }

    //    private void loadCats() {
//        FilterActivity filterActivity = new FilterActivity();
//        service.getAllData(filterActivity.parameters)
//                .subscribe(this::generateDataList,
//                        e -> Log.d("RETROFIT", String.valueOf(e)))
//                .isDisposed();
//    }

    private void loadCats() {
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

    private void generateDataList(List<Cat> photoList) {
        if (resultCats.isEmpty()) {
            resultCats = new ArrayList<>(photoList);
            adapter = new CatRecyclerAdapter(serviceFavourites, context, resultCats);
            binding.catRecyclerView.setAdapter(adapter);
        } else {
            resultCats.addAll(photoList);
            adapter.notifyDataSetChanged();
        }
    }

    public static void setEmail(String newEmail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", newEmail);
        editor.apply();
        email = newEmail;
    }

}
