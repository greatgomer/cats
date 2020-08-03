package com.example.cats.di;

import com.example.cats.api.services.NetworkProvider;
import com.example.cats.ui.home.fragments.cats.CatsFragmentViewModel;
import com.example.cats.ui.home.fragments.favourites.FavouritesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkProvider.class)
public interface AppComponent {
    void inject(CatsFragmentViewModel catsFragment);
    void favourites(FavouritesFragment favoritesFragment);

}