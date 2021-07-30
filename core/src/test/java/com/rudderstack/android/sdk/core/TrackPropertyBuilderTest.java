package com.rudderstack.android.sdk.core;

import android.text.TextUtils;
import android.util.Log;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import static org.robolectric.shadows.ShadowLog.getLogsForTag;

@RunWith(RobolectricTestRunner.class)
public class TrackPropertyBuilderTest {
    RudderProperty rudderProperty;
    private String category = "mock_category";
    private String label = "mock_label";
    private String value = "mock_value";
    MockedStatic<TextUtils> mockTextUtils;

    @Test public void trackPropertyBuilder() {
        mockTextUtils = Mockito.mockStatic(TextUtils.class);

        ShadowLog.reset();
        String message = "category can not be null or empty";
        String TAG = "RudderSDK";
        MockedStatic<RudderLogger> mockRudderLogger = Mockito.mockStatic(RudderLogger.class);
        mockRudderLogger.when(() -> RudderLogger.logError(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + message);
            return null;
        });
        mockTextUtils.when(() -> TextUtils.isEmpty("")).thenReturn(true);

        rudderProperty = new TrackPropertyBuilder()
                .setCategory("")
                .setLabel(label)
                .setValue(value)
                .build();
        Assert.assertNotNull(rudderProperty);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + message, (getLogsForTag(TAG).get(0)).msg);

        ShadowLog.reset();
        rudderProperty = new TrackPropertyBuilder()
                .setCategory(category)
                .setLabel(label)
                .setValue(value)
                .build();
        Assert.assertNotNull(rudderProperty);

        mockTextUtils.close();
        mockRudderLogger.close();
    }
}
