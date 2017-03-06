package com.wassim.mvvmboilerplate.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wassim.mvvmboilerplate.BuildConfig;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.util.MyGsonTypeAdapterFactory;
import com.wassim.mvvmboilerplate.util.Vars;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Wassim on 11/02/2017.
 */

public interface ApiService {

    @GET("movies.json")
    Observable<List<Movie>> getMovies();

    /**
     * Set up a new ApiService
     **/
    class Factory {

        public static ApiService newService() {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(new StethoInterceptor())
                    .build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Vars.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(ApiService.class);
        }
    }
}
