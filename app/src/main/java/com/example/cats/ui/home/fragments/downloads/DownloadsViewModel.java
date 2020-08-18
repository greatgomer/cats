package com.example.cats.ui.home.fragments.downloads;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cats.api.models.res.Downloads;
import com.example.cats.api.services.DownloadsService;
import com.example.cats.databinding.FragmentDownloadsBinding;
import com.example.cats.di.MyApplication;

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

    public List<Downloads> downloads = new ArrayList<>();
    public MutableLiveData<List<Downloads>> resultDownloadsList = new MutableLiveData<>();
    SharedPreferences sharedPreferences;
    public String userName = "";

    public DownloadsViewModel(@NonNull Application application) {
        super(application);
    }

    public void downloadsViewModel(FragmentDownloadsBinding binding){
        context = getApplication();
        ((MyApplication) getApplication().getApplicationContext()).appComponent.downloads(this);
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplication());
        userName = sharedPreferences.getString("userName", "");
        loadDownloads();
    }

    private void loadDownloads() {
        service.getAllDownloads(userName, 20)
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

    public LiveData<List<Downloads>> getResultDownloadsList() {
        if (resultDownloadsList == null) {
            resultDownloadsList = new MutableLiveData<>();
            loadDownloads();
        }
        return resultDownloadsList;
    }

    private void generateDataList(List<Downloads> photoList) {
        downloads.addAll(photoList);
        resultDownloadsList.setValue(downloads);
    }

}
