package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.cats.databinding.ActivityAuthorisatounBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class AuthorisationActivity extends AppCompatActivity {
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthorisatounBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorisatoun);
        setTitle(R.string.authorisation);

        RxView.clicks(binding.button3).subscribe(aVoid -> email = binding.editTextTextPersonName.getText().toString()).isDisposed();
    }

}