package com.example.cats.di;

import com.example.cats.home.CatsFragment;
import com.example.cats.api.services.ProviderService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ProviderService.class)
public interface AppComponent {
    void inject(CatsFragment catsFragment);
}