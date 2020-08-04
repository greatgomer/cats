package com.example.cats.ui.home.fragments.downloadsViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cats.databinding.FragmentDownloadsBinding;

public class DownloadsViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DownloadsViewModel(@NonNull Application application) {
        super(application);
    }

    public void downloadsViewModel(FragmentDownloadsBinding binding){
        context = getApplication();
        binding.fab.setOnClickListener(view ->
                Toast.makeText(context, "ready", Toast.LENGTH_SHORT).show());
    }

}
