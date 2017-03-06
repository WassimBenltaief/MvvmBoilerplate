package com.wassim.mvvmboilerplate.ui.base;


import java.util.List;

import rx.Observable;

public abstract class BaseViewModel<T extends MvvmModel> {

    public abstract Observable<T> getModel();

    public abstract Observable<List<T>> getModels();

}

