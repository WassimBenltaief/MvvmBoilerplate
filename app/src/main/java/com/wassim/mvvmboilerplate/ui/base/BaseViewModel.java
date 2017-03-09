package com.wassim.mvvmboilerplate.ui.base;


import android.databinding.BaseObservable;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

public abstract class BaseViewModel<T extends MvvmModel> extends BaseObservable {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public abstract void unbind(boolean changingConfigurations);

    public abstract void getModels();

    public abstract void onModelsLoaded(List<T> models);

    public abstract void handleError(Throwable throwable);
}

