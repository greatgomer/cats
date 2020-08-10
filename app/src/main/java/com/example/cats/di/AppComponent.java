package com.example.cats.di;

import com.example.cats.api.services.NetworkProvider;
import com.example.cats.ui.home.fragments.downloadsViewModel.deleteViewModel.DeleteViewModel;
import com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel.DownloadsDialog;
import com.example.cats.ui.home.fragments.downloadsViewModel.dialogViewModel.DownloadsDialogViewModel;
import com.example.cats.ui.home.fragments.favouritesViewModel.favouritesDialogViewModel.FavouritesDialogViewModel;
import com.example.cats.ui.home.fragments.catsViewModel.CatsFragmentViewModel;
import com.example.cats.ui.home.fragments.downloadsViewModel.DownloadsViewModel;
import com.example.cats.ui.home.fragments.favouritesViewModel.FavouritesFragmentViewModel;
import com.example.cats.ui.imageViewModel.ImageDetailViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkProvider.class)
public interface AppComponent {
    void inject(CatsFragmentViewModel catsFragment);
    void favourites(FavouritesFragmentViewModel favoritesFragment);
    void favourites(FavouritesDialogViewModel dialogActivity);
    void downloads(DownloadsViewModel downloadsViewModel);
    void download(DownloadsDialogViewModel downloadsDialogViewModel);
    void download(DeleteViewModel deleteViewModel);
    void votes(ImageDetailViewModel imageDetailViewModel);

}