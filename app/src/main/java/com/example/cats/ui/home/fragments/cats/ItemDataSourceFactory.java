package com.example.cats.ui.home.fragments.cats;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;

public class ItemDataSourceFactory extends DataSource.Factory {
    ImagesService service;
    public ItemDataSourceFactory(ImagesService service) {
        this.service = service;
    }

    private MutableLiveData<PageKeyedDataSource<Integer, Cat>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Cat> create() {
        CatDataSource catDataSource = new CatDataSource(service);
        itemLiveDataSource.postValue(catDataSource);
        return catDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Cat>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

}
