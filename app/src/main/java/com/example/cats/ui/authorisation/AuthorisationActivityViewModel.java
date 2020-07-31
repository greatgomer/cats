package com.example.cats.ui.authorisation;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.databinding.ActivityAuthorisatounBinding;
import com.example.cats.ui.home.fragments.cats.CatsFragment;
import com.jakewharton.rxbinding4.view.RxView;

// Used AndroidViewModel, because I use context. If we using ViewModel with context, we can get a memory leak.
public class AuthorisationActivityViewModel extends AndroidViewModel {
    public AuthorisationActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonsClick(ActivityAuthorisatounBinding binding) {
        Context context = getApplication();
        RxView.clicks(binding.buttonApply).subscribe(aVoid -> CatsFragment.setEmail(binding.editTextTextPersonName.getText().toString())).isDisposed();
        RxView.clicks(binding.buttonDelete).subscribe(aVoid -> CatsFragment.setEmail("")).isDisposed();
        RxView.clicks(binding.buttonShowUser).subscribe(aVoid ->{
            assert CatsFragment.email != null;
            if (!CatsFragment.email.equals("")) {
                Toast.makeText(context, CatsFragment.email, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "No added user", Toast.LENGTH_LONG).show();
            }
        }).isDisposed();
    }

}
