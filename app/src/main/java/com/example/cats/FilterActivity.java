package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cats.databinding.ActivityFilterBinding;
import com.example.cats.home.CatsFragment;
import com.jakewharton.rxbinding4.view.RxView;

public class FilterActivity extends AppCompatActivity {
    public static boolean flag = false;

    public FilterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        onButtonPressed(binding);
        setTitle(R.string.choose_filter);
    }

    public void onButtonPressed(ActivityFilterBinding binding) {
        RxView.clicks(binding.button).subscribe(aVoid -> {
            if (binding.filterBoxes.isChecked()) {
                CatsFragment.parameters.put("category_ids", "5");
                flag = true;
            }
            if (binding.filterClothes.isChecked()) {
                CatsFragment.parameters.put("category_ids", "15");
                flag = true;
            }
            if (binding.filterHats.isChecked()) {
                CatsFragment.parameters.put("category_ids", "1");
                flag = true;
            }
            if (binding.filterSinks.isChecked()) {
                CatsFragment.parameters.put("category_ids", "14");
                flag = true;
            }
            if (binding.filterSpace.isChecked()) {
                CatsFragment.parameters.put("category_ids", "2");
                flag = true;
            }
            if (binding.filterSunglasses.isChecked()) {
                CatsFragment.parameters.put("category_ids", "4");
                flag = true;
            }
            if (binding.filterTies.isChecked()) {
                CatsFragment.parameters.put("category_ids", "7");
                flag = true;
            }
        }).isDisposed();

        RxView.clicks(binding.button2).subscribe(aVoid -> {
            CatsFragment.parameters.clear();
            CatsFragment.parameters.put("limit", "14");
            CatsFragment.parameters.put("page", "0");
            flag = true;
        }).isDisposed();
    }

}