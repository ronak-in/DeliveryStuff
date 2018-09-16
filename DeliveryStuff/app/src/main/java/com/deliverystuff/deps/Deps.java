package com.deliverystuff.deps;

import com.deliverystuff.data.SharedPrefsHelper;
import com.deliverystuff.networking.NetworkModule;
import com.deliverystuff.ui.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, SharedPrefsHelper.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
