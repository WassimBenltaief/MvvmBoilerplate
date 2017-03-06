package com.wassim.mvvmboilerplate.util;

/**
 * Created by wassim on 3/6/17.
 */

import com.wassim.mvvmboilerplate.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class that makes instances of data models with random field values.
 */
public class TestDataFactory {


    /**
     * Make a list of customers
     *
     * @param number of customers to create
     * @return list of Customers
     */
    public static List<Movie> makeListMovie(int number) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            movies.add(makeMovie(i));
        }
        return movies;
    }

    private static Movie makeMovie(int index) {
        return Movie.builder()
                .setId(index)
                .setTitle("title" + index)
                .setImage("image" + index)
                .setRating(index)
                .setReleaseYear(index)
                .setGenre("genre" + index)
                .setSynopsis("synopsis" + index)
                .build();
    }
}