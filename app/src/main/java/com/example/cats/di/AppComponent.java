package com.example.cats.di;

import com.example.cats.api.services.NetworkProvider;
import com.example.cats.ui.home.fragments.cats.CatsFragment;
import com.example.cats.ui.home.fragments.downloads.delete.DeleteViewModel;
import com.example.cats.ui.home.fragments.downloads.dialog.DownloadsDialogViewModel;
import com.example.cats.ui.home.fragments.favourites.favouritesDialog.FavouritesDialogViewModel;
import com.example.cats.ui.home.fragments.cats.CatsFragmentViewModel;
import com.example.cats.ui.home.fragments.downloads.DownloadsViewModel;
import com.example.cats.ui.home.fragments.favourites.FavouritesFragmentViewModel;
import com.example.cats.ui.image.ImageDetailViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkProvider.class)
public interface AppComponent {
    void inject(CatsFragmentViewModel catsFragment);
    void favourites(FavouritesFragmentViewModel favoritesFragment);
    void favourite(CatsFragmentViewModel catsFragmentViewModel);
    void favourites(FavouritesDialogViewModel dialogActivity);
    void downloads(DownloadsViewModel downloadsViewModel);
    void download(DownloadsDialogViewModel downloadsDialogViewModel);
    void download(DeleteViewModel deleteViewModel);
    void votes(ImageDetailViewModel imageDetailViewModel);

}