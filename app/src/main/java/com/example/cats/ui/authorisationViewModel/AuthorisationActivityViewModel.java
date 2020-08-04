package com.example.cats.ui.authorisationViewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.databinding.ActivityAuthorisatounBinding;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
import com.jakewharton.rxbinding4.view.RxView;

public class AuthorisationActivityViewModel extends AndroidViewModel {
    public AuthorisationActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonsClick(ActivityAuthorisatounBinding binding) {
        Context context = getApplication();
        RxView.clicks(binding.buttonApply).subscribe(aVoid -> CatsFragmentViewModel.setEmail(binding.editTextTextPersonName.getText().toString())).isDisposed();
        RxView.clicks(binding.buttonDelete).subscribe(aVoid -> CatsFragmentViewModel.setEmail("")).isDisposed();
        RxView.clicks(binding.buttonShowUser).subscribe(aVoid ->{
            assert CatsFragmentViewModel.email != null;
            if (!CatsFragmentViewModel.email.equals("")) {
                Toast.makeText(context, CatsFragmentViewModel.email, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "No added user", Toast.LENGTH_LONG).show();
            }
        }).isDisposed();
    }

}
