package com.example.cats.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cats.R;
import com.example.cats.databinding.ActivityMainBinding;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.page_1:
                navController.navigate(R.id.catsFragment);
                return true;

            case R.id.page_2:
                if (CatsFragmentViewModel.email.equals("default value")) {
                    Toast.makeText(this, "Add user", Toast.LENGTH_LONG).show();
                } else {
                    navController.navigate(R.id.favoritesFragment);
                }
                return true;

            case R.id.page_3:
                if (CatsFragmentViewModel.email.equals("default value")) {
                    Toast.makeText(this, "Add user", Toast.LENGTH_LONG).show();
                } else {
                    navController.navigate(R.id.downloadsFragment);
                }
                return true;
        }

        return false;
    };

}