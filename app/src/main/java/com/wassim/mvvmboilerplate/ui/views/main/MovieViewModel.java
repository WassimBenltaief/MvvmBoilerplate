package com.wassim.mvvmboilerplate.ui.views.main;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.injection.ViewModel;
import com.wassim.mvvmboilerplate.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

import static com.wassim.mvvmboilerplate.util.Vars.LOADED;

/**
 * Created by wassim on 3/6/17.
 */

@ViewModel
public class MovieViewModel extends BaseViewModel<Movie> {

    public ObservableInt recyclerVisibility = new ObservableInt(View.GONE);
    public ObservableInt progressVisibility = new ObservableInt(View.GONE);
    public ObservableInt statusVisibility = new ObservableInt(View.GONE);
    public ObservableField<String> status = new ObservableField<>("No movies have been found.");

    private DataManager dataManager;
    protected List<Movie> movies = new ArrayList<>();

    public BehaviorSubject<Integer> viewModelSubject = BehaviorSubject.create();

    @Inject
    public MovieViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void unbind(boolean changingConfigurations) {
        if (!changingConfigurations && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void getModels() {
        showProgress();
        compositeSubscription.add(dataManager.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onModelsLoaded, this::handleError));
    }

    @Override
    public void onModelsLoaded(List<Movie> movies) {
        this.movies = movies;
        if (movies.isEmpty()) {
            showEmpty();
        } else {
            showList();
        }
    }

    private void showList() {
        hideProgress();
        showRecycler();
        if (viewModelSubject.hasObservers()) {
            viewModelSubject.onNext(LOADED);
        }
    }

    private void showRecycler() {
        recyclerVisibility.set(View.VISIBLE);
    }

    private void showEmpty() {
        hideProgress();
        recyclerVisibility.set(View.GONE);
        statusVisibility.set(View.VISIBLE);
    }

    private void hideProgress() {
        progressVisibility.set(View.GONE);
    }

    private void showProgress() {
        progressVisibility.set(View.VISIBLE);
    }

    @Override
    public void handleError(Throwable throwable) {
        hideProgress();
        Timber.e(throwable, "cannot load movies");
    }
}
