package com.example.cats.ui.home.fragments.cats;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;
import com.example.cats.databinding.FragmentCatsBinding;
import com.example.cats.di.MyApplication;

import javax.inject.Inject;

public class CatsViewModel extends AndroidViewModel {
    @Inject
    ImagesService service;

    public LiveData<PagedList<Cat>> catPagedList;
    public LiveData<PageKeyedDataSource<Integer, Cat>> liveDataSource;
    public final int PAGE_SIZE = 20;
    FragmentCatsBinding binding;

    public CatsViewModel(@NonNull Application application) {
        super(application);
    }

    public void onCatsViewModel(FragmentCatsBinding binding) {
        this.binding = binding;
        ((MyApplication) getApplication().getApplicationContext()).appComponent.inject(this);

        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory(service);
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(PAGE_SIZE).build();

        catPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

}



