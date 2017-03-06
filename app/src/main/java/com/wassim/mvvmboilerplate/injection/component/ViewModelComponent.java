package com.wassim.mvvmboilerplate.injection.component;


import com.wassim.mvvmboilerplate.injection.ViewModel;
import com.wassim.mvvmboilerplate.injection.module.ActivityModule;
import com.wassim.mvvmboilerplate.ui.base.BaseActivity;

import dagger.Component;

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check {@link BaseActivity} to see how this components
 * survives configuration changes.
 * Use the {@link ViewModel} scope to annotate dependencies that need to survive
 * configuration changes (for example ViewModel instances).
 */
@ViewModel
@Component(dependencies = ApplicationComponent.class)
public interface ViewModelComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}