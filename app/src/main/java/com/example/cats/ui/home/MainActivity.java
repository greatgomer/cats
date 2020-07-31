package com.example.cats.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.cats.ui.home.fragments.downloads.DownloadsFragment;
import com.example.cats.R;
import com.example.cats.databinding.ActivityMainBinding;
import com.example.cats.ui.home.fragments.cats.CatsFragment;
import com.example.cats.ui.home.fragments.favorites.FavoritesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new CatsFragment();
    public final Fragment fragment2 = new FavoritesFragment();
    final Fragment fragment3 = new DownloadsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.page_1:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;

            case R.id.page_2:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;

            case R.id.page_3:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
        }

        return false;
    };

}