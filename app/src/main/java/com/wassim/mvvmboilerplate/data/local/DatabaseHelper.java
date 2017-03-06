package com.wassim.mvvmboilerplate.data.local;

import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.wassim.mvvmboilerplate.MovieModel;
import com.wassim.mvvmboilerplate.data.model.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Wassim on 11/02/2017.
 */
@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    /**
     * Inject a debuggable instance
     *
     * @param dbOpenHelper
     */
    @Inject
    DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = new SqlBrite.Builder()
                .logger(message -> Timber.tag("Database").v(message))
                .build()
                .wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
        mDb.setLoggingEnabled(true);
    }

    BriteDatabase getDb() {
        return mDb;
    }

    @SuppressWarnings("deprecation")
    Observable<Movie> setMovies(List<Movie> movies) {
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()) return;
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                for (Movie movie : movies) {
                    long result = mDb.insert(
                            MovieModel.TABLE_NAME,
                            Movie.FACTORY.marshal(movie).asContentValues(),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) subscriber.onNext(movie);
                }
                transaction.markSuccessful();
                subscriber.onCompleted();
            } finally {
                transaction.end();
            }
        });
    }

    Observable<List<Movie>> getMovies() {
        return mDb.createQuery(Movie.TABLE_NAME, Movie.SELECT_ALL)
                .mapToList(Movie.MAPPER_ALL::map);
    }
}
