package com.wassim.mvvmboilerplate;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wassim.mvvmboilerplate.injection.component.ApplicationComponent;
import com.wassim.mvvmboilerplate.injection.component.DaggerApplicationComponent;
import com.wassim.mvvmboilerplate.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Wassim on 11/02/2017.
 */
public class App extends Application {

    private ApplicationComponent mApplicationComponent;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            );
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            );
        }
        checkLeakCanaryHeapProcess();
    }

    /**
     * if we're not in Test mode check then LeakCanary.isInAnalyzerProcess
     */
    private void checkLeakCanaryHeapProcess() {
        if (!App.get(this).getApplicationInfo().dataDir.contains("robolectric")) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                return;
            }
            mRefWatcher = LeakCanary.install(this);
        }
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    /**
     * Replace component from Tests
     *
     * @param applicationComponent to set in test mode
     */
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
