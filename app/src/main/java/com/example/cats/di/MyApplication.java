package com.example.cats.di;

import android.app.Application;

public class MyApplication extends Application {
    public AppComponent appComponent = DaggerAppComponent.create();

}