package com.example.cats.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.FilterActivity;
import com.example.cats.R;
import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;
import com.example.cats.di.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatsFragment extends Fragment {
    @Inject
    ImagesService service;

    private CatRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    List<Cat> resultCats = new ArrayList<>();

    public CatsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cats, container, false);
        recyclerView = view.findViewById(R.id.catRecyclerView);
        ((MyApplication) Objects.requireNonNull(getActivity()).getApplicationContext()).appComponent.inject(this);
        addMoreCatsInFragment();
        setHasOptionsMenu(true);
        addCatInFavourites();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cat_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id != R.id.action_favorite) {
            return super.onOptionsItemSelected(item);
        }
        Intent intent = new Intent(getContext(), FilterActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    private void addMoreCatsInFragment() {
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        service.getAllCats(FilterActivity.parameters)
                .enqueue(new Callback<List<Cat>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                        generateDataList(response.body());
                    }
                    @Override
                    public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void generateDataList(List<Cat> photoList) {
        if (resultCats.isEmpty()) {
            resultCats = new ArrayList<>(photoList);
            adapter = new CatRecyclerAdapter(getActivity(), resultCats);
            recyclerView.setAdapter(adapter);
        } else {
            resultCats.addAll(photoList);
            adapter.notifyDataSetChanged();
        }
    }

    private void addCatInFavourites() {
//        FavoritesParameters favoritesParameters = new FavoritesParameters("3");
//        Call<FavoritesParameters> call = service.postFavourites(favoritesParameters);
//        call.enqueue(new Callback<FavoritesParameters>() {
//            @Override
//            public void onResponse(@NotNull Call<FavoritesParameters> call, @NotNull Response<FavoritesParameters> response) {
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<FavoritesParameters> call, @NotNull Throwable t) {
//            }
//        });
    }

}