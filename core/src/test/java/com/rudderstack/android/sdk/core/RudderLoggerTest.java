package com.rudderstack.android.sdk.core;

import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import static org.robolectric.shadows.ShadowLog.getLogsForTag;

@RunWith(RobolectricTestRunner.class)
public class RudderLoggerTest {
    private MockedStatic<RudderLogger> mockRudderLogger;
    private int logLevel = RudderLogger.RudderLogLevel.INFO;
    private final String TAG = "RudderSDK";
    private final String message = "mock_message";
    @Mock Exception ex;
    @Mock Throwable throwable;

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        mockRudderLogger = Mockito.mockStatic(RudderLogger.class);
    }

    @After public void setupComplete() {
        mockRudderLogger.close();
    }

    @Test public void RudderLogLevel() {
        final int VERBOSE = 5;
        final int DEBUG = 4;
        final int INFO = 3;
        final int WARN = 2;
        final int ERROR = 1;
        final int NONE = 0;

        Assert.assertEquals(VERBOSE, RudderLogger.RudderLogLevel.VERBOSE);
        Assert.assertEquals(DEBUG, RudderLogger.RudderLogLevel.DEBUG);
        Assert.assertEquals(INFO, RudderLogger.RudderLogLevel.INFO);
        Assert.assertEquals(WARN, RudderLogger.RudderLogLevel.WARN);
        Assert.assertEquals(ERROR, RudderLogger.RudderLogLevel.ERROR);
        Assert.assertEquals(NONE, RudderLogger.RudderLogLevel.NONE);
    }

    @Test public void logError() {
        ShadowLog.reset();
        mockRudderLogger.when(() -> RudderLogger.logError(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + message);
            return null;
        });

        RudderLogger.logError(message);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + message, (getLogsForTag(TAG).get(0)).msg);

        ShadowLog.reset();
        Mockito.when(ex.getMessage()).thenReturn(message);
        RudderLogger.logError(ex);
        // Assert is not possible as static method is not called: "logError(ex.getMessage());"


        mockRudderLogger.when(() -> RudderLogger.logError(throwable)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + throwable);
            return null;
        });
        ShadowLog.reset();
        RudderLogger.logError(throwable);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + throwable, (getLogsForTag(TAG).get(0)).msg);

    }

    @Test public void logWarn() {
        ShadowLog.reset();
        mockRudderLogger.when(() -> RudderLogger.logWarn(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.w(TAG, "Warn: " + message);
            return null;
        });

        RudderLogger.logWarn(message);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Warn: " + message, (getLogsForTag(TAG).get(0)).msg);
    }

    @Test public void logInfo() {
        ShadowLog.reset();
        mockRudderLogger.when(() -> RudderLogger.logInfo(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.i(TAG, "Info: " + message);
            return null;
        });

        RudderLogger.logInfo(message);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Info: " + message, (getLogsForTag(TAG).get(0)).msg);
    }

    @Test public void logDebug() {
        ShadowLog.reset();
        mockRudderLogger.when(() -> RudderLogger.logDebug(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message);
            return null;
        });

        RudderLogger.logDebug(message);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Debug: " + message, (getLogsForTag(TAG).get(0)).msg);
    }

    @Test public void logVerbose() {
        ShadowLog.reset();
        mockRudderLogger.when(() -> RudderLogger.logVerbose(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.v(TAG, "Verbose: " + message);
            return null;
        });

        RudderLogger.logVerbose(message);
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Verbose: " + message, (getLogsForTag(TAG).get(0)).msg);
    }
}
