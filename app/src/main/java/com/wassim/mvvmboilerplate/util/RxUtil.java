package com.wassim.mvvmboilerplate.util;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wassim on 3/6/17.
 */

public class RxUtil {

    public static <U> Subscription subscribeSetter(final Observable<U> observable,
                                                    final Action1<U> setter,
                                                    final Action1<Throwable> error,
                                                    final String tag) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SetterSubscriber<>(setter, error, tag));
    }
}
