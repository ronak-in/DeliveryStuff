package com.deliverystuff.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.deliverystuff.deps.DaggerDeps;
import com.deliverystuff.deps.Deps;
import com.deliverystuff.networking.NetworkModule;

import java.io.File;

public class BaseApp extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder()
                .networkModule(new NetworkModule(cacheFile, BaseApp.this))
                .build();
    }

    public Deps getDeps() {
        return deps;
    }
}
