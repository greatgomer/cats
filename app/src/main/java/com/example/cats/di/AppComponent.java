package com.example.cats.di;

import com.example.cats.api.services.NetworkProvider;
import com.example.cats.ui.dialogViewModel.DialogViewModel;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
import com.example.cats.ui.home.fragments.favouritesViewModel.FavouritesFragmentViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkProvider.class)
public interface AppComponent {
    void inject(CatsFragmentViewModel catsFragment);
    void favourites(FavouritesFragmentViewModel favoritesFragment);
    void favourites(DialogViewModel dialogActivity);

}