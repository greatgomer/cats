package com.example.cats.ui.home.fragments.downloadsViewModel.deleteViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.cats.R;
import com.example.cats.databinding.ActivityDeleteFromDownloadsBinding;
import com.jakewharton.rxbinding4.view.RxView;

public class DeleteDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDeleteFromDownloadsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_from_downloads);
        DeleteViewModel model = ViewModelProviders.of(this).get(DeleteViewModel.class);

        RxView.clicks(binding.buttonDelete).subscribe(aVoid ->{
            model.deleteDownloads();
            onBackPressed();
        }).isDisposed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}