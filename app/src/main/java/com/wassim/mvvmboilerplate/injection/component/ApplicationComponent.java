package com.wassim.mvvmboilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.local.DatabaseHelper;
import com.wassim.mvvmboilerplate.data.remote.ApiService;
import com.wassim.mvvmboilerplate.injection.ApplicationContext;
import com.wassim.mvvmboilerplate.injection.module.ApplicationModule;
import com.wassim.mvvmboilerplate.util.NetworkUtil;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    ApiService apiService();

    DatabaseHelper databaseHelper();

    DataManager dataManager();

    NetworkUtil networkUtil();
}
