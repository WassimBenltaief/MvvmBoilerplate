package com.wassim.mvvmboilerplate.ui.views.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.wassim.mvvmboilerplate.R;
import com.wassim.mvvmboilerplate.databinding.ActivityMainBinding;
import com.wassim.mvvmboilerplate.ui.base.BaseActivity;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wassim.mvvmboilerplate.util.Vars.LOADED;

public class MainActivity extends BaseActivity {

    @Inject
    MovieViewModel viewModel;

    private ActivityMainBinding activityMainBinding;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        initDataBinding();
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new MoviesAdapter();
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.recyclerView.setHasFixedSize(true);
        activityMainBinding.recyclerView.setNestedScrollingEnabled(true);
        activityMainBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.viewModelSubject
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMovies, this::handleError);
        if(viewModel.movies.isEmpty()){
            viewModel.getModels();
        }
    }

    private void handleError(Throwable throwable) {
        Throwable throwable1 = throwable;
    }

    @Override
    protected void onDestroy() {
        viewModel.unbind(isChangingConfigurations());
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(viewModel);
        activityMainBinding.setView(this);
        setSupportActionBar(activityMainBinding.toolbar);
    }

    private void showMovies(Integer integer){
        if(integer == LOADED)
        mAdapter.setMovies(viewModel.movies);
    }
}
