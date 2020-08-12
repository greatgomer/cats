package com.example.cats.ui.filter;

import androidx.lifecycle.ViewModel;

import com.example.cats.ui.home.fragments.cats.CatsFragmentViewModel;

public class FilterActivityViewModel extends ViewModel {
    public void onButtonPressed() {
            CatsFragmentViewModel.parameters.clear();
            CatsFragmentViewModel.parameters.put("limit", "14");
            CatsFragmentViewModel.parameters.put("page", "0");
    }

}
