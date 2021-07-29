package com.rudderstack.android.sdk.core;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class RudderDeviceinfoTest {
    private RudderDeviceInfo rudderDeviceInfo;
    private Application application;
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        application = ApplicationProvider.getApplicationContext();
        try (MockedStatic<RudderClient> mockApplication = Mockito.mockStatic(RudderClient.class)) {
            mockApplication.when(RudderClient::getApplication).thenReturn(application);
            rudderDeviceInfo = new RudderDeviceInfo("mocked_AdvertisingId");
        }
    }

    @Test
    public void testFun(){
        int i=6;
    }
}
