package com.example.cats.dagger;

import android.app.Application;

public class MyApplication extends Application {
    public static AppComponent appComponent = DaggerAppComponent.create();
}