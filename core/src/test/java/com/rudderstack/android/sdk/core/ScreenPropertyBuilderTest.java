package com.rudderstack.android.sdk.core;

import android.text.TextUtils;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import java.util.HashMap;
import java.util.Map;

import static org.robolectric.shadows.ShadowLog.getLogsForTag;

@RunWith(RobolectricTestRunner.class)
public class ScreenPropertyBuilderTest {
    private String name = "mock_name";
    private boolean isAutomatic = false;
    MockedStatic<TextUtils> mockTextUtils;

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        mockTextUtils = Mockito.mockStatic(TextUtils.class);
    }

    @After public void setupComplete() {
        mockTextUtils.close();
    }

    @Test public void setupTest() {
        RudderProperty rudderProperty;
        Map<String, Object> map = new HashMap<>();
        map.put("automatic", isAutomatic);

        ShadowLog.reset();
        String message = "name can not be empty";
        String TAG = "RudderSDK";
        mockTextUtils.when(() -> TextUtils.isEmpty("")).thenReturn(true);
        MockedStatic<RudderLogger> mockRudderLogger = Mockito.mockStatic(RudderLogger.class);
        mockRudderLogger.when(() -> RudderLogger.logError(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + message);
            return null;
        });
        rudderProperty= new ScreenPropertyBuilder()
                .setScreenName("")
                .isAtomatic(isAutomatic)
                .build();
        Assert.assertNotNull(rudderProperty);
        Assert.assertEquals(map, rudderProperty.getMap());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + message, (getLogsForTag(TAG).get(0)).msg);

        map.put("name", name);
        rudderProperty = new ScreenPropertyBuilder()
                .setScreenName(name)
                .isAtomatic(isAutomatic)
                .build();
        Assert.assertNotNull(rudderProperty);
        Assert.assertEquals(map, rudderProperty.getMap());
    }
}
