package com.example.cats.ui.home.fragments.cats.authorisation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cats.R;
import com.example.cats.databinding.ActivityAuthorisatounBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class AuthorisationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        ActivityAuthorisatounBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorisatoun);
        userName = sharedPreferences.getString("userName", "");
        setTitle(R.string.authorisation);

        RxView.clicks(binding.buttonDelete).subscribe(aVoid ->{
            userName = ("");
            rememberUser();
        }).isDisposed();
        RxView.clicks(binding.buttonShowUser).subscribe(aVoid -> Toast.makeText(this, userName, Toast.LENGTH_SHORT).show()).isDisposed();
        RxView.clicks(binding.buttonApply).subscribe(aVoid ->{
            userName = binding.editTextTextPersonName.getText().toString();
            rememberUser();
            onBackPressed();
        }).isDisposed();
    }

    public void rememberUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}