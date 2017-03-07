package com.wassim.mvvmboilerplate.ui.views.main;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.util.TestDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Before
    public void setUp() {
        movieViewModel = new MovieViewModel(mockDataManager);
    }

    @Test
    public void getModel() throws Exception {

        TestSubscriber<Movie> result = new TestSubscriber<>();

        movieViewModel.getModel().subscribe(result);
        result.assertTerminalEvent();
        result.assertError(RuntimeException.class);


    }

    @Test
    public void getModels() throws Exception {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        when(mockDataManager.getMovies()).thenReturn(Observable.just(movies));

        TestSubscriber<List<Movie>> result = new TestSubscriber<>();
        movieViewModel.getModels().subscribe(result);

        result.assertNoErrors();
        result.assertValue(movies);
        result.assertCompleted();

        verify(mockDataManager).getMovies();
        verifyNoMoreInteractions(mockDataManager);

    }

}