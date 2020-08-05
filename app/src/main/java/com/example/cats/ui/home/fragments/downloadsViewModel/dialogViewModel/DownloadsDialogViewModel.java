package com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.databinding.ActivityDownloadsDialogBinding;

public class DownloadsDialogViewModel extends AndroidViewModel {
    ActivityDownloadsDialogBinding binding;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DownloadsDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public void downloadsViewModel(ActivityDownloadsDialogBinding binding) {
        this.binding = binding;
        context = getApplication();
    }

}
