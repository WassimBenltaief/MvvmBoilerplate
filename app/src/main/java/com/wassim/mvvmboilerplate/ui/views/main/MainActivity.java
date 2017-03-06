package com.wassim.mvvmboilerplate.ui.views.main;

import android.os.Bundle;

import com.wassim.mvvmboilerplate.R;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @Inject
    MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityComponent().inject(this);
    }

    @Override
    public void bind() {
        compositeSubscription.add(viewModel.getModels()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMoviesLoaded, this::handleError));
    }

    private void onMoviesLoaded(List<Movie> movies) {
        Timber.d(movies.toString());
    }

    private void handleError(Throwable throwable) {
        Timber.d(throwable.toString());
    }

}
