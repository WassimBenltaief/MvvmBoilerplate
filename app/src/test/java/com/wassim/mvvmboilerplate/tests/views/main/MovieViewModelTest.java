package com.wassim.mvvmboilerplate.tests.views.main;

import android.view.View;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.ui.views.main.MovieViewModel;
import com.wassim.mvvmboilerplate.util.RxSchedulersOverrideRule;
import com.wassim.mvvmboilerplate.util.TestDataFactory;
import com.wassim.mvvmboilerplate.util.Vars;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by wassim on 3/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieViewModelTest {

    @Mock
    DataManager mockDataManager;

    private MovieViewModel movieViewModel;

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        movieViewModel = new MovieViewModel(mockDataManager);
    }


    @Test
    public void getModels() throws Exception {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        when(mockDataManager.getMovies()).thenReturn(Observable.just(movies));

        movieViewModel.getModels();

        verify(mockDataManager).getMovies();
        verifyNoMoreInteractions(mockDataManager);

    }

    @Test
    public void onModelsLoaded() throws Exception {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        TestSubscriber<Integer> result = new TestSubscriber<>();
        movieViewModel.viewModelSubject.subscribe(result);

        movieViewModel.onModelsLoaded(movies);

        Assert.assertEquals(View.GONE, movieViewModel.progressVisibility.get());
        Assert.assertEquals(View.GONE, movieViewModel.statusVisibility.get());
        Assert.assertEquals(View.VISIBLE, movieViewModel.recyclerVisibility.get());

        result.assertValue(Vars.LOADED);
        result.assertNotCompleted();

    }

    @Test
    public void onModelsLoadedEmpty() throws Exception {

        TestSubscriber<Integer> result = new TestSubscriber<>();
        movieViewModel.viewModelSubject.subscribe(result);

        movieViewModel.onModelsLoaded(Collections.emptyList());

        Assert.assertEquals(View.GONE, movieViewModel.progressVisibility.get());
        Assert.assertEquals(View.VISIBLE, movieViewModel.statusVisibility.get());
        Assert.assertEquals(View.GONE, movieViewModel.recyclerVisibility.get());

        result.assertNoValues();
        result.assertNotCompleted();

    }
}