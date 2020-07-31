package com.example.cats.ui.authorisation;

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
        model.onButtonsClick(binding);
    }

}