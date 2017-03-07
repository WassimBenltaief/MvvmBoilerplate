package com.wassim.mvvmboilerplate.data.local;

import android.content.Context;

import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.data.remote.ApiService;
import com.wassim.mvvmboilerplate.util.NetworkUtil;
import com.wassim.mvvmboilerplate.util.TestDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by wassim on 3/6/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    ApiService mMockApiService;
    @Mock
    DatabaseHelper mMockDatabaseHelper;
    @Mock
    Context mMockContext;
    @Mock
    NetworkUtil mMockNetworkUtil;

    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockApiService, mMockDatabaseHelper,
                mMockContext, mMockNetworkUtil);
    }

    @Test
    public void getMoviesCallsRetrofitAndEmitsValues() {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        when(mMockNetworkUtil.isNetworkConnected(any())).thenReturn(true);
        when(mMockApiService.getMovies()).thenReturn(Observable.just(movies));
        when(mMockDatabaseHelper.setMovies(any())).thenReturn(Observable.just(movies));

        TestSubscriber<List<Movie>> result = new TestSubscriber<>();
        mDataManager.getMovies().subscribe(result);

        result.assertNoErrors();
        result.assertValue(movies);
        result.assertCompleted();
        result.assertUnsubscribed();

        verify(mMockDatabaseHelper, never()).getMovies();
    }

    @Test
    public void getMoviesNoInternet() {

        when(mMockNetworkUtil.isNetworkConnected(any()))
                .thenReturn(false);

        TestSubscriber<List<Movie>> result = new TestSubscriber<>();
        mDataManager.getMovies().subscribe(result);

        result.assertNoErrors();
        result.assertValue(Collections.emptyList());
        result.assertCompleted();
        result.assertUnsubscribed();

        verify(mMockApiService, never()).getMovies();
        verify(mMockDatabaseHelper, never()).getMovies();
    }

    @Test
    public void getMoviesCallsRetrofitAndSaveInDatabase() {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        when(mMockNetworkUtil.isNetworkConnected(any())).thenReturn(true);
        when(mMockApiService.getMovies()).thenReturn(Observable.just(movies));
        when(mMockDatabaseHelper.setMovies(movies)).thenReturn(Observable.just(movies));

        mDataManager.getMovies().subscribe();

        verify(mMockApiService).getMovies();
        verify(mMockDatabaseHelper).setMovies(movies);
        verify(mMockDatabaseHelper, never()).getMovies();
    }

    @Test
    public void getMoviesSwitchIfEmpty() {

        when(mMockNetworkUtil.isNetworkConnected(any()))
                .thenReturn(true);

        when(mMockApiService.getMovies())
                .thenReturn(Observable.just(Collections.emptyList()));

        when(mMockDatabaseHelper.setMovies(any()))
                .thenReturn(Observable.just(Collections.emptyList()));

        TestSubscriber<List<Movie>> result = new TestSubscriber<>();
        mDataManager.getMovies().subscribe(result);

        result.assertNoErrors();
        result.assertValue(Collections.emptyList());
        result.assertCompleted();
        result.assertUnsubscribed();

        verify(mMockApiService, times(1)).getMovies();
        verify(mMockDatabaseHelper, times(1)).setMovies(any());
        verify(mMockDatabaseHelper, never()).getMovies();
        verifyNoMoreInteractions(mMockApiService, mMockDatabaseHelper);
    }

    @Test
    public void onErrorResumeNextCallsDatabase() {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        when(mMockNetworkUtil.isNetworkConnected(any()))
                .thenReturn(true);

        when(mMockApiService.getMovies())
                .thenReturn(Observable.error(new UnknownHostException()));

        when(mMockDatabaseHelper.getMovies())
                .thenReturn(Observable.just(movies));

        mDataManager.getMovies().subscribe();

        verify(mMockApiService, times(1)).getMovies();
        verify(mMockDatabaseHelper, never()).setMovies(any());
        verify(mMockDatabaseHelper, times(1)).getMovies();

    }

}
