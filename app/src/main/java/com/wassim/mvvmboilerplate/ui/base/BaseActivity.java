package com.wassim.mvvmboilerplate.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.squareup.leakcanary.RefWatcher;
import com.wassim.mvvmboilerplate.App;
import com.wassim.mvvmboilerplate.injection.component.ActivityComponent;
import com.wassim.mvvmboilerplate.injection.component.DaggerViewModelComponent;
import com.wassim.mvvmboilerplate.injection.component.ViewModelComponent;
import com.wassim.mvvmboilerplate.injection.module.ActivityModule;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of PersistentViewModel survive
 * across configuration changes.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ViewModelComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ViewModelComponent viewModelComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ViewModelComponent id=%d", mActivityId);
            viewModelComponent = DaggerViewModelComponent.builder()
                    .applicationComponent(App.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, viewModelComponent);
        } else {
            Timber.i("Reusing ViewModelComponent id=%d", mActivityId);
            viewModelComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = viewModelComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ViewModelComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

}
