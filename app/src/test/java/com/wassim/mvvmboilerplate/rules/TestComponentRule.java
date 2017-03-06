package com.wassim.mvvmboilerplate.rules;

import android.content.Context;

import com.wassim.mvvmboilerplate.App;
import com.wassim.mvvmboilerplate.injection.component.ApplicationComponent;
import com.wassim.mvvmboilerplate.injection.component.DaggerApplicationComponent;
import com.wassim.mvvmboilerplate.injection.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private final ApplicationComponent mApplicationComponent;
    private final Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
        App application = App.get(context);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationTestModule(application))
                .build();
    }

    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                App application = App.get(mContext);
                application.setComponent(mApplicationComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }
}
