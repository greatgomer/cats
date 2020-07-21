package com.example.cats.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityFilterBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class FilterActivity extends AppCompatActivity {
    public static String link = "images/search?limit=14";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        onButtonPressed(binding);
    }

    public void onButtonPressed(ActivityFilterBinding binding){
        RxView.clicks(binding.button).subscribe(aVoid-> link = "images/search?limit=14").isDisposed();
        RxView.clicks(binding.button2).subscribe(aVoid-> link = "images/search?category_ids=5&limit=14").isDisposed();
        RxView.clicks(binding.button3).subscribe(aVoid-> link = "images/search?category_ids=15&limit=14").isDisposed();
        RxView.clicks(binding.button4).subscribe(aVoid-> link = "images/search?category_ids=1&limit=14").isDisposed();
        RxView.clicks(binding.button5).subscribe(aVoid-> link = "images/search?category_ids=14&limit=14").isDisposed();
        RxView.clicks(binding.button6).subscribe(aVoid-> link = "images/search?category_ids=2&limit=14").isDisposed();
        RxView.clicks(binding.button7).subscribe(aVoid-> link = "images/search?category_ids=4&limit=14").isDisposed();
        RxView.clicks(binding.button8).subscribe(aVoid-> link = "images/search?category_ids=7&limit=14").isDisposed();
    }
}