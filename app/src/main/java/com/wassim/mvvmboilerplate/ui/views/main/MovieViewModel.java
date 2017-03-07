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
public class MovieViewModel extends BaseViewModel<Movie> {

    private DataManager dataManager;

    @Inject
    public MovieViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Observable<Movie> getModel() {
        return Observable.error(new RuntimeException("Not yet implemented method !"));
    }

    @Override
    public Observable<List<Movie>> getModels() {
        return dataManager.getMovies();
    }
}
