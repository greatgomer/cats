package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cats.databinding.ActivityAuthorisatounBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class AuthorisationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthorisatounBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_authorisatoun);
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        setTitle(R.string.authorisation);
        onButtonsClick(binding);
    }

    private void onButtonsClick(ActivityAuthorisatounBinding binding) {
        RxView.clicks(binding.buttonApply).subscribe(aVoid -> {
            email = binding.editTextTextPersonName.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.apply();
        }).isDisposed();

        RxView.clicks(binding.buttonDelete).subscribe(aVoid -> {
            email = "";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.apply();
        }).isDisposed();

        RxView.clicks(binding.buttonShowUser).subscribe(aVoid ->{
            email = sharedPreferences.getString("email", "default value");
            assert email != null;
            if (!email.equals("")) {
                Toast.makeText(this, email, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No added user", Toast.LENGTH_LONG).show();
            }
        }).isDisposed();
    }

}