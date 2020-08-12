package com.example.cats.ui.home.fragments.downloads.delete;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.R;
import com.example.cats.api.models.req.DeleteFromDownloads;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.di.MyApplication;
import com.example.cats.ui.home.MainActivity;
import com.example.cats.ui.home.fragments.downloads.DownloadsFragmentAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteViewModel extends AndroidViewModel {
    @Inject
    DownloadsService service;

    public DeleteViewModel(@NonNull Application application) {
        super(application);
    }

    public void deleteDownloads() {
        Context context = getApplication();
        ((MyApplication) getApplication().getApplicationContext()).appComponent.download(this);
        service.deleteFromDownloads(DownloadsFragmentAdapter.idImageDownloads).enqueue(new Callback<DeleteFromDownloads>() {
            @Override
            public void onResponse(@NotNull Call<DeleteFromDownloads> call, @NotNull Response<DeleteFromDownloads> response) {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<DeleteFromDownloads> call, @NotNull Throwable t) {
                Toast.makeText(context, "Decline", Toast.LENGTH_SHORT).show();
            }
        });
        MainActivity.navController.navigate(R.id.downloadsFragment);
    }

}
