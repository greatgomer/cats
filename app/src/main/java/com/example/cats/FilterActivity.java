package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cats.databinding.ActivityFilterBinding;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.HashMap;

public class FilterActivity extends AppCompatActivity {
    public static HashMap<String, String> parameters = new HashMap<>();
    public static boolean flag = false;

    public FilterActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        onButtonPressed(binding);
        parameters.put("limit", "14");
        parameters.put("page", "0");
    }

    public void onButtonPressed(ActivityFilterBinding binding) {
        RxView.clicks(binding.filterBoxes).subscribe(aVoid-> {parameters.put("category_ids", "5"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterClothes).subscribe(aVoid-> {parameters.put("category_ids", "15"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterHats).subscribe(aVoid-> {parameters.put("category_ids", "1"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterSinks).subscribe(aVoid-> {parameters.put("category_ids", "14"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterSpace).subscribe(aVoid-> {parameters.put("category_ids", "2"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterSunglasses).subscribe(aVoid-> {parameters.put("category_ids", "4"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterTies).subscribe(aVoid-> {parameters.put("category_ids", "7"); flag = true;}).isDisposed();
        RxView.clicks(binding.filterReset)
                .subscribe(aVoid-> {parameters.clear(); parameters.put("limit", "14"); parameters.put("page", "0"); flag = true;
                        }).isDisposed();
    }

}