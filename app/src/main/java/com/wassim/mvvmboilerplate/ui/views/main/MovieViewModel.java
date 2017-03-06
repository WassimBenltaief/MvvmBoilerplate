package com.wassim.mvvmboilerplate.ui.views.main;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.injection.ViewModel;
import com.wassim.mvvmboilerplate.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;
import rx.Observable;

/**
 * Created by wassim on 3/6/17.
 */

@ViewModel
class MovieViewModel extends BaseViewModel<Movie> {

    private DataManager dataManager;

    @Inject
    public MovieViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Observable<Movie> getModel() {
        return null;
    }

    @Override
    public Observable<List<Movie>> getModels() {
        return dataManager.getMovies();
    }
}
