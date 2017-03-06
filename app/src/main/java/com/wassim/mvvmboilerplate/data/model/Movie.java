package com.wassim.mvvmboilerplate.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.wassim.mvvmboilerplate.MovieModel;
import com.wassim.mvvmboilerplate.ui.base.MvvmModel;

/**
 * Created by wassim on 3/6/17.
 */

@AutoValue
public abstract class Movie extends MvvmModel implements MovieModel, Parcelable {

    public static TypeAdapter<Movie> typeAdapter(Gson gson) {
        return new AutoValue_Movie.GsonTypeAdapter(gson);
    }
}
