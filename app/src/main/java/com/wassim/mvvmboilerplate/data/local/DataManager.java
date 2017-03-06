package com.wassim.mvvmboilerplate.data.local;

import android.content.Context;

import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.data.remote.ApiService;
import com.wassim.mvvmboilerplate.injection.ApplicationContext;
import com.wassim.mvvmboilerplate.util.NetworkUtil;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private final Context context;
    private final ApiService apiService;
    private final DatabaseHelper databaseHelper;
    private NetworkUtil networkUtil;

    @Inject
    public DataManager(ApiService apiService, DatabaseHelper databaseHelper,
                       @ApplicationContext Context context, NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.context = context;
        this.apiService = apiService;
        this.databaseHelper = databaseHelper;
    }

    public Observable<List<Movie>> getMovies() {
        return apiService.getMovies();
    }
}
