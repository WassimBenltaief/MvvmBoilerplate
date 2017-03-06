package com.wassim.mvvmboilerplate.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.RowMapper;
import com.wassim.mvvmboilerplate.MovieModel;
import com.wassim.mvvmboilerplate.ui.base.MvvmModel;

/**
 * Created by wassim on 3/6/17.
 */

@AutoValue
public abstract class Movie extends MvvmModel implements MovieModel, Parcelable {

    public static final Factory<Movie> FACTORY = new Factory<>(AutoValue_Movie::new);

    public static final RowMapper<Movie> MAPPER_ALL = FACTORY.select_allMapper();
    public static final RowMapper<Movie> MAPPER_BY_ID = FACTORY.select_by_idMapper();
    public static final RowMapper<Movie> TITLE_MAPPER = FACTORY.select_by_titleMapper();
    public static final RowMapper<Movie> YEAR_MAPPER = FACTORY.select_by_release_yearMapper();

    public static TypeAdapter<Movie> typeAdapter(Gson gson) {
        return new AutoValue_Movie.GsonTypeAdapter(gson);
    }
}
