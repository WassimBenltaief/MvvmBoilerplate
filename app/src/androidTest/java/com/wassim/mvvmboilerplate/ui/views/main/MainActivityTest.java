package com.wassim.mvvmboilerplate.ui.views.main;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.wassim.mvvmboilerplate.rules.IntegrationTestComponentRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Created by wassim on 3/7/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final IntegrationTestComponentRule component =
            new IntegrationTestComponentRule(InstrumentationRegistry.getTargetContext());

    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void showCustomers() throws Exception {

    }
}