package com.example.cats.di;

import com.example.cats.api.services.ProviderCat;
import com.example.cats.favorites.FavoritesFragment;
import com.example.cats.home.CatsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ProviderCat.class)
public interface AppComponent {
    void inject(CatsFragment catsFragment);
    void favorites(FavoritesFragment favoritesFragment);
}