package com.example.cats.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cats.R;
import com.example.cats.model.JSONPlaceHolderApi;
import com.example.cats.model.NetworkService;
import com.example.cats.model.Cat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatsFragment extends Fragment {
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
        addMoreCatsInFragment();
        return view;
    }

    private void addMoreCatsInFragment(){
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

    private void loadCats(){
        JSONPlaceHolderApi service = NetworkService.getInstance().create(JSONPlaceHolderApi.class);
        Call<List<Cat>> call = service.getAllData();
        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }

        private void generateDataList (List<Cat> photoList) {
                if(resultCats.isEmpty()){
                    resultCats = new ArrayList<>(photoList);
                    adapter = new CatRecyclerAdapter(getActivity(), resultCats);
                    recyclerView.setAdapter(adapter);
                }else{
                    resultCats.addAll(photoList);
                    adapter.notifyDataSetChanged();
                }
        }
        });
    }
}