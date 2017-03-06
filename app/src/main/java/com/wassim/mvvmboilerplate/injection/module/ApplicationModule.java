package com.wassim.mvvmboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.local.DatabaseHelper;
import com.wassim.mvvmboilerplate.data.remote.ApiService;
import com.wassim.mvvmboilerplate.injection.ApplicationContext;
import com.wassim.mvvmboilerplate.util.NetworkUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


    /** to mock in tests module **/

    @Provides
    @Singleton
    NetworkUtil providesNetworkUtil(){
        return new NetworkUtil();
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiService.Factory.newService();
    }

    @Provides
    @Singleton
    DataManager providesDataManager(ApiService apiService, DatabaseHelper databaseHelper,
                                    @ApplicationContext Context context, NetworkUtil networkUtil) {
        return new DataManager(apiService, databaseHelper, context, networkUtil);
    }

}
