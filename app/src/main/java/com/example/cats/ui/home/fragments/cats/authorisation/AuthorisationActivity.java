package com.example.cats.ui.home.fragments.cats.authorisation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityAuthorisatounBinding;

public class AuthorisationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthorisatounBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorisatoun);
        AuthorisationActivityViewModel model = ViewModelProviders.of(this).get(AuthorisationActivityViewModel.class);
        setTitle(R.string.authorisation);
        model.onButtonsClick();

//        RxView.clicks(binding.buttonDelete).subscribe(aVoid -> model.setEmail("")).isDisposed();
//        RxView.clicks(binding.buttonShowUser).subscribe(aVoid -> model.checkOnUser()).isDisposed();
//        RxView.clicks(binding.buttonApply).subscribe(aVoid ->{
//            model.setEmail(binding.editTextTextPersonName.getText().toString());
//            onBackPressed();
//        }).isDisposed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}