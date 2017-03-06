package com.wassim.mvvmboilerplate.data.local;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;
import javax.inject.Singleton;

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
}
