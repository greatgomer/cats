package com.example.cats.dagger;

import com.example.cats.home.CatsFragment;
import com.example.cats.model.NetworkService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkService.class})
public interface AppComponent{
    void inject(CatsFragment catsFragment);
}