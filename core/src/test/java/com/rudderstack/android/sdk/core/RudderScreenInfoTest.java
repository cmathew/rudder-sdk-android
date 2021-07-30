package com.rudderstack.android.sdk.core;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RudderScreenInfoTest {
    RudderScreenInfo rudderScreenInfo;
    Application application;

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        application = ApplicationProvider.getApplicationContext();
        rudderScreenInfo = new RudderScreenInfo(application);
    }

    @Test public void setupTest(){
        Assert.assertTrue(true);
    }

}
