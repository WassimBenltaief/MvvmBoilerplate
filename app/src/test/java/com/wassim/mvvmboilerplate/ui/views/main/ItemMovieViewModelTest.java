package com.wassim.mvvmboilerplate.ui.views.main;

import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.util.TestDataFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Wassim on 09/03/2017.
 */
public class ItemMovieViewModelTest {

    private ItemMovieViewModel itemMovieViewModel;
    private Movie movie;

    @Before
    public void setUp() {
        movie = TestDataFactory.makeMovie(1);
        itemMovieViewModel = new ItemMovieViewModel(movie);
    }

    @Test
    public void getTitle() throws Exception {
        Assert.assertEquals("title1", itemMovieViewModel.getTitle());
    }

    @Test
    public void getGenre() throws Exception {
        Assert.assertEquals("genre1", itemMovieViewModel.getGenre());
    }

    @Test
    public void getUrl() throws Exception {
        Assert.assertEquals("image1", itemMovieViewModel.getUrl());
    }

    @Test
    public void setMovie() throws Exception {
        Movie movie2 = TestDataFactory.makeMovie(2);
        itemMovieViewModel.setMovie(movie2);
        Assert.assertEquals("title2", itemMovieViewModel.getTitle());
    }

}