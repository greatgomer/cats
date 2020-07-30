package com.example.cats.di;

import com.example.cats.api.services.NetworkProvider;
import com.example.cats.favorites.FavoritesFragment;
import com.example.cats.home.CatsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkProvider.class)
public interface AppComponent {
    void inject(CatsFragment catsFragment);
    void favourites(FavoritesFragment favoritesFragment);

}