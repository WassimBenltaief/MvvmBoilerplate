package com.wassim.mvvmboilerplate.injection.component;


import com.wassim.mvvmboilerplate.injection.PerActivity;
import com.wassim.mvvmboilerplate.injection.module.ActivityModule;
import com.wassim.mvvmboilerplate.ui.views.main.MainActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
