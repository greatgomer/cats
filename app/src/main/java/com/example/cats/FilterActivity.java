package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.example.cats.databinding.ActivityFilterBinding;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.HashMap;

public class FilterActivity extends AppCompatActivity {
    public HashMap<String, String> parameters = new HashMap<>();
    private Context context;

    public FilterActivity(Context context) {
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        onButtonPressed(binding);

        parameters.put("limit", "14");
        parameters.put("page", "0");
    }

    public void onButtonPressed(ActivityFilterBinding binding) {
        RxView.clicks(binding.filterBoxes).subscribe(aVoid-> parameters.put("category_ids", "5")).isDisposed();
        RxView.clicks(binding.filterClothes).subscribe(aVoid-> parameters.put("category_ids", "15")).isDisposed();
        RxView.clicks(binding.filterHats).subscribe(aVoid-> parameters.put("category_ids", "1")).isDisposed();
        RxView.clicks(binding.filterSinks).subscribe(aVoid-> parameters.put("category_ids", "14")).isDisposed();
        RxView.clicks(binding.filterSpace).subscribe(aVoid-> parameters.put("category_ids", "2")).isDisposed();
        RxView.clicks(binding.filterSunglasses).subscribe(aVoid->parameters.put("category_ids", "4")).isDisposed();
        RxView.clicks(binding.filterTies).subscribe(aVoid-> parameters.put("category_ids", "7")).isDisposed();
        RxView.clicks(binding.filterReset)
                .subscribe(aVoid-> {parameters.clear(); parameters.put("limit", "14"); parameters.put("page", "0");
                        }
                ).isDisposed();
    }

}