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

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    NetworkUtil mNetworkUtil;

    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockApiService, mMockDatabaseHelper,
                mMockContext, mNetworkUtil);
    }

    @Test
    public void getMoviesCallsRetrofitAndEmitsValues() {
        List<Movie> movies = TestDataFactory.makeListMovie(2);

        when(mNetworkUtil.isNetworkConnected(any())).thenReturn(true);
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

        when(mNetworkUtil.isNetworkConnected(any()))
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

}
