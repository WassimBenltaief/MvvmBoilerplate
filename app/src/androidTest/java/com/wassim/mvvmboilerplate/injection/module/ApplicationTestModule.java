package com.wassim.mvvmboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import com.wassim.mvvmboilerplate.data.local.DataManager;
import com.wassim.mvvmboilerplate.data.local.DatabaseHelper;
import com.wassim.mvvmboilerplate.data.remote.ApiService;
import com.wassim.mvvmboilerplate.injection.ApplicationContext;
import com.wassim.mvvmboilerplate.util.NetworkUtil;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
public class ApplicationTestModule extends ApplicationModule {


    public ApplicationTestModule(Application application) {
        super(application);
    }

    /*************
     * MOCKS
     *************/

    @Override
    NetworkUtil providesNetworkUtil() {
        return mock(NetworkUtil.class);
    }

    @Override
    ApiService provideApiService() {
        return mock(ApiService.class);
    }

    @Override
    DataManager providesDataManager(ApiService apiService, DatabaseHelper databaseHelper,
                                    @ApplicationContext Context context, NetworkUtil networkUtil) {
        return mock(DataManager.class);
    }

}
