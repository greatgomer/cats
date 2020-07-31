package com.example.cats.di;

import com.example.cats.api.services.NetworkProvider;
import com.example.cats.ui.home.fragments.cats.CatsFragmentViewModel;
import com.example.cats.ui.home.fragments.favorites.FavoritesFragment;
import com.example.cats.ui.home.fragments.cats.CatsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkProvider.class)
public interface AppComponent {
    void inject(CatsFragmentViewModel catsFragment);
    void favourites(FavoritesFragment favoritesFragment);

}