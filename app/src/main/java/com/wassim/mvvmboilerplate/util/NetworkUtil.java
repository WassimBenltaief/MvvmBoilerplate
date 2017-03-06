package com.wassim.mvvmboilerplate.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.adapter.rxjava.HttpException;

@Singleton
public class NetworkUtil {


    @Inject
    public NetworkUtil() {
    }

    /**
     * Returns true if the Throwable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    public boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof HttpException
                && ((HttpException) throwable).code() == statusCode;
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}