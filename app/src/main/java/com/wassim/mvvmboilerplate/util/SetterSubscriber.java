package com.wassim.mvvmboilerplate.util;

import rx.Subscriber;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by wassim on 3/6/17.
 */

public class SetterSubscriber<U> extends Subscriber<U> {

    final private Action1<U> setter;
    final private Action1<Throwable> error;
    final private String tag;

    public SetterSubscriber(final Action1<U> setter,
                            final Action1<Throwable> error,
                            final String tag) {
        this.setter = setter;
        this.error = error;
        this.tag = tag;
    }

    @Override
    public void onCompleted() {
        Timber.d(tag + "." + "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e, tag + "." + "onError");
        error.call(e);
    }

    @Override
    public void onNext(U u) {
        Timber.d(tag + "." + "onNext");
        setter.call(u);
    }
}

