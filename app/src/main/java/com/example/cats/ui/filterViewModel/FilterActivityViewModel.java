package com.example.cats.ui.filterViewModel;

import androidx.lifecycle.ViewModel;

import com.example.cats.databinding.ActivityFilterBinding;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
import com.jakewharton.rxbinding4.view.RxView;

public class FilterActivityViewModel extends ViewModel {
    public static boolean flag = false;

    public void onButtonPressed(ActivityFilterBinding binding) {
        RxView.clicks(binding.button).subscribe(aVoid -> {
            if (binding.filterBoxes.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "5");
                flag = true;
            }
            if (binding.filterClothes.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "15");
                flag = true;
            }
            if (binding.filterHats.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "1");
                flag = true;
            }
            if (binding.filterSinks.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "14");
                flag = true;
            }
            if (binding.filterSpace.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "2");
                flag = true;
            }
            if (binding.filterSunglasses.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "4");
                flag = true;
            }
            if (binding.filterTies.isChecked()) {
                CatsFragmentViewModel.parameters.put("category_ids", "7");
                flag = true;
            }
        }).isDisposed();

        RxView.clicks(binding.button2).subscribe(aVoid -> {
            CatsFragmentViewModel.parameters.clear();
            CatsFragmentViewModel.parameters.put("limit", "14");
            CatsFragmentViewModel.parameters.put("page", "0");
            flag = true;
        }).isDisposed();
    }

}
