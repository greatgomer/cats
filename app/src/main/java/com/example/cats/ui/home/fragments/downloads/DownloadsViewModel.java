package com.example.cats.ui.home.fragments.downloads;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cats.api.models.res.Downloads;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.FragmentDownloadsBinding;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.fragments.cats.CatsFragmentViewModel;
import com.example.cats.ui.home.fragments.downloads.dialog.DownloadsDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadsViewModel extends AndroidViewModel {
    @Inject
    DownloadsService service;

    @SuppressLint("StaticFieldLeak")
    Context context;
    LinearLayoutManager mLayoutManager;
    FragmentDownloadsBinding binding;

    DownloadsFragmentAdapter adapter;
    public List<Downloads> downloads = new ArrayList<>();

    public DownloadsViewModel(@NonNull Application application) {
        super(application);
    }

    public void downloadsViewModel(FragmentDownloadsBinding binding){
        this.binding = binding;
        context = getApplication();
        mLayoutManager = new GridLayoutManager(context, 2);
        binding.downloadsRecyclerView.setLayoutManager(mLayoutManager);
        ((MyApplication) getApplication().getApplicationContext()).appComponent.downloads(this);
        loadFavourites();
        onFabClick();
    }

    public void onFabClick(){
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(context, DownloadsDialog.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    private void loadFavourites() {
        service.getAllDownloads(CatsFragmentViewModel.email, 20)
                .enqueue(new Callback<List<Downloads>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Downloads>> call, @NotNull Response<List<Downloads>> response) {
                        generateDataList(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Downloads>> call, @NotNull Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void generateDataList(List<Downloads> photoList) {
        downloads.addAll(photoList);
        adapter = new DownloadsFragmentAdapter(context, downloads);
        binding.downloadsRecyclerView.setAdapter(adapter);
    }

}
