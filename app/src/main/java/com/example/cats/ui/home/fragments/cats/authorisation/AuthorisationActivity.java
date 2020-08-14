package com.example.cats.ui.home.fragments.cats.authorisation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cats.R;
import com.example.cats.databinding.ActivityAuthorisatounBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class AuthorisationActivity extends AppCompatActivity {
    public static String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthorisatounBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorisatoun);
        setTitle(R.string.authorisation);

        RxView.clicks(binding.buttonDelete).subscribe(aVoid -> userName = ("")).isDisposed();
        RxView.clicks(binding.buttonShowUser).subscribe(aVoid -> Toast.makeText(this, userName, Toast.LENGTH_SHORT).show()).isDisposed();
        RxView.clicks(binding.buttonApply).subscribe(aVoid ->{
            userName = binding.editTextTextPersonName.getText().toString();
            onBackPressed();
        }).isDisposed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}