package com.example.cats.ui.home.fragments.cats.filter;

import androidx.lifecycle.ViewModel;

import com.example.cats.ui.home.fragments.cats.CatDataSource;

public class FilterActivityViewModel extends ViewModel {
    public void onButtonPressed() {
        CatDataSource.parameters.clear();
        CatDataSource.parameters.put("limit", "14");
        CatDataSource.parameters.put("page", "0");
    }

}
