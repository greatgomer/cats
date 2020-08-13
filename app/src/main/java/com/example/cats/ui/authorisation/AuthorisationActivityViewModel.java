package com.example.cats.ui.authorisation;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AuthorisationActivityViewModel extends AndroidViewModel {
    static SharedPreferences sharedPreferences;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public AuthorisationActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonsClick() {
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        context = getApplication();
    }

//    public void checkOnUser() {
//        assert CatsFragmentViewModel.email != null;
//        if (!CatsFragmentViewModel.email.equals("")) {
//            Toast.makeText(context, CatsFragmentViewModel.email, Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context, "Add user", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public void setEmail(String newEmail) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("email", newEmail);
//        editor.apply();
//        CatsFragmentViewModel.email = newEmail;
//    }

}