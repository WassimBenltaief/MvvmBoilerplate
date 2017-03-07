package com.wassim.mvvmboilerplate.data.local;

import android.database.Cursor;

import com.wassim.mvvmboilerplate.BuildConfig;
import com.wassim.mvvmboilerplate.MovieModel;
import com.wassim.mvvmboilerplate.data.model.Movie;
import com.wassim.mvvmboilerplate.util.DefaultConfig;
import com.wassim.mvvmboilerplate.util.RxSchedulersOverrideRule;
import com.wassim.mvvmboilerplate.util.TestDataFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    private final DatabaseHelper mDatabaseHelper =
            new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application));

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Test
    public void setMovies() {
        Movie movie1 = TestDataFactory.makeMovie(1);
        Movie movie2 = TestDataFactory.makeMovie(2);
        List<Movie> movies = Arrays.asList(movie1, movie2);

        TestSubscriber<List<Movie>> result = new TestSubscriber<>();
        mDatabaseHelper.setMovies(movies).subscribe(result);
        result.assertNoErrors();
        result.assertValue(movies);


        Cursor cursor = mDatabaseHelper.getDb().query(MovieModel.SELECT_ALL);
        assertEquals(2, cursor.getCount());
        for (Movie movie : movies) {
            cursor.moveToNext();
            assertEquals(movie.id(), Movie.MAPPER_ALL.map(cursor).id());
        }
    }

    @Test
    public void getMovies() {
        Movie movie1 = TestDataFactory.makeMovie(1);
        Movie movie2 = TestDataFactory.makeMovie(2);
        List<Movie> movies = Arrays.asList(movie1, movie2);

        mDatabaseHelper.setMovies(movies).subscribe();

        TestSubscriber<List<Movie>> result = new TestSubscriber<>();
        mDatabaseHelper.getMovies().subscribe(result);
        result.assertNoErrors();
        result.assertValue(movies);
    }

}