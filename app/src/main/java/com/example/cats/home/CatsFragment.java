package com.example.cats.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cats.R;
import com.example.cats.activities.FilterActivity;
import com.example.cats.dagger.MyApplication;
import com.example.cats.model.JSONPlaceHolderApi;
import com.example.cats.model.Cat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CatsFragment extends Fragment {
    private CatRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    List<Cat> resultCats = new ArrayList<>();
    public static String link = "images/search?limit=14";

    @Inject JSONPlaceHolderApi service;

    public CatsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cats, container, false);
        recyclerView = view.findViewById(R.id.catRecyclerView);


        addMoreCatsInFragment();
        setHasOptionsMenu(true);

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("limit", "14");
        parameters.put("page", "0");
        service.getAllData(parameters).subscribe(
                cats -> {
                    Log.d("TAG", "cats");
                });
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
        if(!link.equals(FilterActivity.link)){
            link = FilterActivity.link;
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new CatsFragment()).commit();
        }
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
//        JSONPlaceHolderApi service = NetworkService.getInstance().create(JSONPlaceHolderApi.class);
//        Call<List<Cat>> call = service.getAllData(link);


//        call.enqueue(new Callback<List<Cat>>() {
//            @Override
//            public void onResponse(@NotNull Call<List<Cat>> call, @NotNull Response<List<Cat>> response) {
//                generateDataList(response.body());
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<List<Cat>> call, @NotNull Throwable t) {
//                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }

//        private void generateDataList (List<Cat> photoList) {
//                if(resultCats.isEmpty()){
//                    resultCats = new ArrayList<>(photoList);
//                    adapter = new CatRecyclerAdapter(getActivity(), resultCats);
//                    recyclerView.setAdapter(adapter);
//                }else{
//                    resultCats.addAll(photoList);
//                    adapter.notifyDataSetChanged();
//                }
//        }
//        });
    }
}