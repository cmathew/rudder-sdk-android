package com.rudderstack.android.sdk.core;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;
import com.rudderstack.android.sdk.core.util.Utils;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.rudderstack.android.sdk.core.Constants.DATA_PLANE_URL;
import static org.robolectric.shadows.ShadowLog.getLogsForTag;

@RunWith(RobolectricTestRunner.class)
public class RudderConfigTest {
    private RudderConfig rudderConfig;
    private MockedStatic<RudderLogger> mockRudderLogger;
    private MockedStatic<TextUtils> textUtils;
    private MockedStatic<URLUtil> urlUtil;
    private String logMessage;
    final private String TAG = "RudderSDK";
    RudderConfig rudderConfigBuilder;
    @Mock RudderIntegration.Factory factory;
    List<RudderIntegration.Factory> expectedFactory;
    List<RudderIntegration.Factory> expectedFactoryList;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        rudderConfig = new RudderConfig();
        mockRudderLogger = Mockito.mockStatic(RudderLogger.class);

        expectedFactory = new ArrayList<>();
        expectedFactory.add(factory);
        RudderIntegration.Factory factory2 = Mockito.mock(RudderIntegration.Factory.class);
        RudderIntegration.Factory factory3 = Mockito.mock(RudderIntegration.Factory.class);
        RudderIntegration.Factory factory4 = Mockito.mock(RudderIntegration.Factory.class);
        RudderIntegration.Factory factory5 = Mockito.mock(RudderIntegration.Factory.class);
        expectedFactoryList = new ArrayList<>();
        expectedFactoryList.add(factory);
        expectedFactoryList.add(factory2);
        expectedFactoryList.add(factory3);
        expectedFactoryList.add(factory4);
        expectedFactoryList.add(factory5);
    }

    @After
    public void complete() {
        mockRudderLogger.close();
    }

    @Test
    public void testFun() {
        int i =6;
    }

    @Test
    public void init() {
        RudderConfig testRudderConfig;
        textUtils = Mockito.mockStatic(TextUtils.class);
        urlUtil = Mockito.mockStatic(URLUtil.class);
        logMessage = "endPointUri can not be null or empty. Set to default.";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Debug: " + logMessage);
            return null;
        });
        textUtils.when(() -> TextUtils.isEmpty(DATA_PLANE_URL)).thenReturn(true);
        testRudderConfig = new RudderConfig();
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Debug: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        textUtils.when(() -> TextUtils.isEmpty(DATA_PLANE_URL)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(DATA_PLANE_URL)).thenReturn(false);
        logMessage = "Malformed endPointUri. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Debug: " + logMessage);
            return null;
        });
        testRudderConfig = new RudderConfig();
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Debug: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // Skipping the 'flushQueueSize', 'dbCountThreshold', 'configRefreshInterval', 'sleepTimeOut'
        // as there is no dependencies/method in 'if' block.

        // Skipping 'factories' as we can't pass factories object;

        textUtils.when(() -> TextUtils.isEmpty(Constants.CONTROL_PLANE_URL)).thenReturn(true);
        logMessage = "configPlaneUrl can not be null or empty. Set to default.";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Debug: " + logMessage);
            return null;
        });
        testRudderConfig = new RudderConfig();
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Debug: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        textUtils.when(() -> TextUtils.isEmpty(Constants.CONTROL_PLANE_URL)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(DATA_PLANE_URL)).thenReturn(false);
        logMessage = "Malformed configPlaneUrl. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Debug: " + logMessage);
            return null;
        });
        testRudderConfig = new RudderConfig();
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Debug: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        textUtils.close();
        urlUtil.close();
    }

    @Test
    public void getter() {
        Assert.assertEquals(DATA_PLANE_URL + "/", rudderConfig.getEndPointUri());
        Assert.assertEquals(DATA_PLANE_URL + "/", rudderConfig.getDataPlaneUrl());
        Assert.assertEquals(Constants.FLUSH_QUEUE_SIZE , rudderConfig.getFlushQueueSize());
        Assert.assertEquals(Constants.DB_COUNT_THRESHOLD , rudderConfig.getDbCountThreshold());
        Assert.assertEquals(Constants.SLEEP_TIMEOUT , rudderConfig.getSleepTimeOut());
        Assert.assertEquals(RudderLogger.RudderLogLevel.ERROR , rudderConfig.getLogLevel());
        Assert.assertEquals(Constants.CONFIG_REFRESH_INTERVAL , rudderConfig.getConfigRefreshInterval());
        Assert.assertEquals(Constants.TRACK_LIFECYCLE_EVENTS , rudderConfig.isTrackLifecycleEvents());
        Assert.assertEquals(Constants.RECORD_SCREEN_VIEWS , rudderConfig.isRecordScreenViews());
        Assert.assertNull(rudderConfig.getFactories());
        Assert.assertEquals(Constants.CONTROL_PLANE_URL + "/" , rudderConfig.getConfigPlaneUrl());
        Assert.assertEquals(Constants.CONTROL_PLANE_URL + "/" , rudderConfig.getControlPlaneUrl());
    }

    @Test
    public void setter() {
        final int CONFIG_REFRESH_INTERVAL = 200;
        final String DATA_PLANE_URL = "https://mock.DATA_PLANE_URL.com";
        final int FLUSH_QUEUE_SIZE = 300;
        final int DB_COUNT_THRESHOLD = 10;
        final int SLEEP_TIMEOUT = 1000;
        final String CONTROL_PLANE_URL = "https://mock.controlPlane.com";
        final boolean TRACK_LIFECYCLE_EVENTS = false;
        final boolean RECORD_SCREEN_VIEWS = true;

        rudderConfig.setDataPlaneUrl(DATA_PLANE_URL);
        Assert.assertEquals(DATA_PLANE_URL, rudderConfig.getDataPlaneUrl());

        rudderConfig.setControlPlaneUrl(CONTROL_PLANE_URL);
        Assert.assertEquals(CONTROL_PLANE_URL, rudderConfig.getControlPlaneUrl());

        rudderConfig.setFlushQueueSize(FLUSH_QUEUE_SIZE);
        Assert.assertEquals(FLUSH_QUEUE_SIZE, rudderConfig.getFlushQueueSize());

        rudderConfig.setDbCountThreshold(DB_COUNT_THRESHOLD);
        Assert.assertEquals(DB_COUNT_THRESHOLD, rudderConfig.getDbCountThreshold());

        rudderConfig.setSleepTimeOut(SLEEP_TIMEOUT);
        Assert.assertEquals(SLEEP_TIMEOUT, rudderConfig.getSleepTimeOut());

        rudderConfig.setLogLevel(RudderLogger.RudderLogLevel.DEBUG);
        Assert.assertEquals(RudderLogger.RudderLogLevel.DEBUG, rudderConfig.getLogLevel());

        rudderConfig.setFactories(expectedFactoryList);
        Assert.assertEquals(expectedFactoryList, rudderConfig.getFactories());

        rudderConfig.setConfigRefreshInterval(CONFIG_REFRESH_INTERVAL);
        Assert.assertEquals(CONFIG_REFRESH_INTERVAL, rudderConfig.getConfigRefreshInterval());

        rudderConfig.setTrackLifecycleEvents(TRACK_LIFECYCLE_EVENTS);
        Assert.assertEquals(TRACK_LIFECYCLE_EVENTS, rudderConfig.isTrackLifecycleEvents());

        rudderConfig.setRecordScreenViews(RECORD_SCREEN_VIEWS);
        Assert.assertEquals(RECORD_SCREEN_VIEWS, rudderConfig.isRecordScreenViews());
    }

    @Test
    public void to_string() {
        String toString = String.format(Locale.US, "RudderConfig: endPointUrl:%s | " +
                        "flushQueueSize: %d | dbCountThreshold: %d | sleepTimeOut: %d | logLevel: %d",
                rudderConfig.getDataPlaneUrl(),
                rudderConfig.getFlushQueueSize(),
                rudderConfig.getDbCountThreshold(),
                rudderConfig.getSleepTimeOut(),
                rudderConfig.getLogLevel());
        Assert.assertEquals(toString, rudderConfig.toString());
    }

    @Test
    public void withFactory() {
        rudderConfigBuilder = new RudderConfig.Builder()
                .withFactory(factory)
                .build();
        Assert.assertEquals(expectedFactory, rudderConfigBuilder.getFactories());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withFactories(expectedFactoryList)
                .build();
        Assert.assertEquals(expectedFactoryList, rudderConfigBuilder.getFactories());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withFactories(factory)
                .build();
        Assert.assertEquals(expectedFactory, rudderConfigBuilder.getFactories());
    }

    @Test
    public void withEndPointUri() {
        textUtils = Mockito.mockStatic(TextUtils.class);
        urlUtil = Mockito.mockStatic(URLUtil.class);
        ShadowLog.reset();

        // Null case
        logMessage = "endPointUri can not be null or empty. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        textUtils.when(() -> TextUtils.isEmpty(null)).thenReturn(true);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withEndPointUri(null)
                .build();
        Assert.assertEquals(Constants.DATA_PLANE_URL, rudderConfigBuilder.getDataPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // Invalid endPointUri case
        String endPointUri = "https://mocked.new.rudderlabs.com";
        logMessage = "Malformed endPointUri. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        textUtils.when(() -> TextUtils.isEmpty(endPointUri)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(endPointUri)).thenReturn(false);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withEndPointUri(endPointUri)
                .build();
        Assert.assertEquals(Constants.DATA_PLANE_URL, rudderConfigBuilder.getDataPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // Valid case
        textUtils.when(() -> TextUtils.isEmpty(endPointUri)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(endPointUri)).thenReturn(true);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withEndPointUri(endPointUri)
                .build();
        Assert.assertEquals(endPointUri + "/", rudderConfigBuilder.getDataPlaneUrl());
        textUtils.close();
        urlUtil.close();
    }

    @Test
    public void withDataPlaneUrl() {
        textUtils = Mockito.mockStatic(TextUtils.class);
        urlUtil = Mockito.mockStatic(URLUtil.class);
        ShadowLog.reset();

        // Null case
        logMessage = "endPointUri can not be null or empty. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        textUtils.when(() -> TextUtils.isEmpty(null)).thenReturn(true);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withDataPlaneUrl(null)
                .build();
        Assert.assertEquals(Constants.DATA_PLANE_URL, rudderConfigBuilder.getDataPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // Invalid dataPlaneUrl case
        String dataPlaneUrl = "https://mocked.new.rudderlabs.com";
        logMessage = "Malformed endPointUri. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        textUtils.when(() -> TextUtils.isEmpty(dataPlaneUrl)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(dataPlaneUrl)).thenReturn(false);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withDataPlaneUrl(dataPlaneUrl)
                .build();
        Assert.assertEquals(Constants.DATA_PLANE_URL, rudderConfigBuilder.getDataPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // Valid case
        textUtils.when(() -> TextUtils.isEmpty(dataPlaneUrl)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(dataPlaneUrl)).thenReturn(true);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withDataPlaneUrl(dataPlaneUrl)
                .build();
        Assert.assertEquals(dataPlaneUrl + "/", rudderConfigBuilder.getDataPlaneUrl());
        textUtils.close();
        urlUtil.close();
    }

    @Test
    public void withFlushQueueSize() {
        int flushQueueSize;
        ShadowLog.reset();

        logMessage = "flushQueueSize is out of range. Min: 1, Max: 100. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });

        // When flushQueueSize < Utils.MIN_FLUSH_QUEUE_SIZE
        flushQueueSize = Utils.MIN_FLUSH_QUEUE_SIZE - 1;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withFlushQueueSize(flushQueueSize)
                .build();
        Assert.assertEquals(Constants.FLUSH_QUEUE_SIZE, rudderConfigBuilder.getFlushQueueSize());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When flushQueueSize > Utils.MAX_FLUSH_QUEUE_SIZE
        flushQueueSize = Utils.MAX_FLUSH_QUEUE_SIZE + 1;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withFlushQueueSize(flushQueueSize)
                .build();
        Assert.assertEquals(Constants.FLUSH_QUEUE_SIZE, rudderConfigBuilder.getFlushQueueSize());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When flushQueueSize = Utils.MAX_FLUSH_QUEUE_SIZE
        flushQueueSize = Utils.MAX_FLUSH_QUEUE_SIZE;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withFlushQueueSize(flushQueueSize)
                .build();
        Assert.assertEquals(flushQueueSize, rudderConfigBuilder.getFlushQueueSize());
    }

    @Test
    public void withDebug() {
        rudderConfigBuilder = new RudderConfig.Builder()
                .withDebug(true)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.DEBUG, rudderConfigBuilder.getLogLevel());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withDebug(false)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.NONE, rudderConfigBuilder.getLogLevel());
    }

    @Test
    public void withLogLevel() {
        rudderConfigBuilder = new RudderConfig.Builder()
                .withLogLevel(RudderLogger.RudderLogLevel.NONE)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.NONE, rudderConfigBuilder.getLogLevel());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withLogLevel(RudderLogger.RudderLogLevel.ERROR)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.ERROR, rudderConfigBuilder.getLogLevel());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withLogLevel(RudderLogger.RudderLogLevel.WARN)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.WARN, rudderConfigBuilder.getLogLevel());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withLogLevel(RudderLogger.RudderLogLevel.INFO)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.INFO, rudderConfigBuilder.getLogLevel());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withLogLevel(RudderLogger.RudderLogLevel.DEBUG)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.DEBUG, rudderConfigBuilder.getLogLevel());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withLogLevel(RudderLogger.RudderLogLevel.VERBOSE)
                .build();
        Assert.assertEquals(RudderLogger.RudderLogLevel.VERBOSE, rudderConfigBuilder.getLogLevel());
    }

    @Test
    public void withDbThresholdCount() {
        int dbThresholdCount;
        ShadowLog.reset();

        // When dbThresholdCount < 0
        dbThresholdCount = -1;
        logMessage = "invalid dbCountThreshold. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withDbThresholdCount(dbThresholdCount)
                .build();
        Assert.assertEquals(Constants.DB_COUNT_THRESHOLD, rudderConfigBuilder.getDbCountThreshold());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When dbThresholdCount >= 0;
        dbThresholdCount = 1;
        logMessage = "invalid dbCountThreshold. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withDbThresholdCount(dbThresholdCount)
                .build();
        Assert.assertEquals(dbThresholdCount, rudderConfigBuilder.getDbCountThreshold());
    }

    @Test
    public void withSleepCount() {
        int sleepTimeOut;
        ShadowLog.reset();

        // When sleepTimeOut < Utils.MIN_SLEEP_TIMEOUT
        sleepTimeOut = Utils.MIN_SLEEP_TIMEOUT - 1;
        logMessage = "invalid sleepTimeOut. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withSleepCount(sleepTimeOut)
                .build();
        Assert.assertEquals(Constants.SLEEP_TIMEOUT, rudderConfigBuilder.getSleepTimeOut());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When sleepTimeOut = Utils.MIN_SLEEP_TIMEOUT
        sleepTimeOut = Utils.MIN_SLEEP_TIMEOUT;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withSleepCount(sleepTimeOut)
                .build();
        Assert.assertEquals(sleepTimeOut, rudderConfigBuilder.getSleepTimeOut());
    }

    @Test
    public void withConfigRefreshInterval() {
        int configRefreshInterval;

        // When configRefreshInterval > Utils.MAX_CONFIG_REFRESH_INTERVAL
        configRefreshInterval = Utils.MAX_CONFIG_REFRESH_INTERVAL + 1;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withConfigRefreshInterval(configRefreshInterval)
                .build();
        Assert.assertEquals(Utils.MAX_CONFIG_REFRESH_INTERVAL, rudderConfigBuilder.getConfigRefreshInterval());

        // When configRefreshInterval < Utils.MIN_CONFIG_REFRESH_INTERVAL
        configRefreshInterval = Utils.MIN_CONFIG_REFRESH_INTERVAL - 1;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withConfigRefreshInterval(configRefreshInterval)
                .build();
        Assert.assertEquals(Utils.MIN_CONFIG_REFRESH_INTERVAL, rudderConfigBuilder.getConfigRefreshInterval());

        // When configRefreshInterval = Utils.MAX_CONFIG_REFRESH_INTERVAL
        configRefreshInterval = Utils.MAX_CONFIG_REFRESH_INTERVAL;
        rudderConfigBuilder = new RudderConfig.Builder()
                .withConfigRefreshInterval(configRefreshInterval)
                .build();
        Assert.assertEquals(configRefreshInterval, rudderConfigBuilder.getConfigRefreshInterval());
    }

    @Test
    public void withRecordScreenViews() {
        rudderConfigBuilder = new RudderConfig.Builder()
                .withRecordScreenViews(true)
                .build();
        Assert.assertTrue(rudderConfigBuilder.isRecordScreenViews());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withRecordScreenViews(false)
                .build();
        Assert.assertFalse(rudderConfigBuilder.isRecordScreenViews());
    }

    @Test
    public void withTrackLifecycleEvents() {
        rudderConfigBuilder = new RudderConfig.Builder()
                .withTrackLifecycleEvents(true)
                .build();
        Assert.assertTrue(rudderConfigBuilder.isTrackLifecycleEvents());

        rudderConfigBuilder = new RudderConfig.Builder()
                .withTrackLifecycleEvents(false)
                .build();
        Assert.assertFalse(rudderConfigBuilder.isTrackLifecycleEvents());
    }

    @Test
    public void withConfigPlaneUrl() {
        final String configPlaneUrl = "https://mock.config.rudderlabs.com";;
        textUtils = Mockito.mockStatic(TextUtils.class);
        urlUtil = Mockito.mockStatic(URLUtil.class);
        ShadowLog.reset();

        // When configPlaneUrl is null
        textUtils.when(() -> TextUtils.isEmpty(null)).thenReturn(true);
        logMessage = "configPlaneUrl can not be null or empty. Set to default.";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withConfigPlaneUrl(null)
                .build();
        Assert.assertEquals(Constants.CONTROL_PLANE_URL, rudderConfigBuilder.getControlPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When configPlaneUrl is not null but invalid
        textUtils.when(() -> TextUtils.isEmpty(configPlaneUrl)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(configPlaneUrl)).thenReturn(false);
        logMessage = "Malformed configPlaneUrl. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withConfigPlaneUrl(configPlaneUrl)
                .build();
        Assert.assertEquals(Constants.CONTROL_PLANE_URL, rudderConfigBuilder.getControlPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When configPlaneUrl is not null but invalid
        textUtils.when(() -> TextUtils.isEmpty(configPlaneUrl)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(configPlaneUrl)).thenReturn(true);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withConfigPlaneUrl(configPlaneUrl)
                .build();
        Assert.assertEquals(configPlaneUrl + "/", rudderConfigBuilder.getControlPlaneUrl());

        textUtils.close();
        urlUtil.close();
    }

    @Test
    public void withControlPlaneUrl() {
        final String controlPlaneUrl = "https://mock.config.rudderlabs.com";
        textUtils = Mockito.mockStatic(TextUtils.class);
        urlUtil = Mockito.mockStatic(URLUtil.class);
        ShadowLog.reset();

        // When controlPlaneUrl is null
        textUtils.when(() -> TextUtils.isEmpty(null)).thenReturn(true);
        logMessage = "configPlaneUrl can not be null or empty. Set to default.";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withControlPlaneUrl(null)
                .build();
        Assert.assertEquals(Constants.CONTROL_PLANE_URL, rudderConfigBuilder.getControlPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When controlPlaneUrl is not null but invalid
        textUtils.when(() -> TextUtils.isEmpty(controlPlaneUrl)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(controlPlaneUrl)).thenReturn(false);
        logMessage = "Malformed configPlaneUrl. Set to default";
        mockRudderLogger.when(() -> RudderLogger.logError(logMessage)).thenAnswer((Answer<Void>) invocation -> {
            Log.e(TAG, "Error: " + logMessage);
            return null;
        });
        rudderConfigBuilder = new RudderConfig.Builder()
                .withControlPlaneUrl(controlPlaneUrl)
                .build();
        Assert.assertEquals(Constants.CONTROL_PLANE_URL, rudderConfigBuilder.getControlPlaneUrl());
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Error: " + logMessage, (getLogsForTag(TAG).get(0)).msg);
        ShadowLog.reset();

        // When controlPlaneUrl is not null but invalid
        textUtils.when(() -> TextUtils.isEmpty(controlPlaneUrl)).thenReturn(false);
        urlUtil.when(() -> URLUtil.isValidUrl(controlPlaneUrl)).thenReturn(true);
        rudderConfigBuilder = new RudderConfig.Builder()
                .withControlPlaneUrl(controlPlaneUrl)
                .build();
        Assert.assertEquals(controlPlaneUrl + "/", rudderConfigBuilder.getControlPlaneUrl());

        textUtils.close();
        urlUtil.close();
    }

    @Test
    public void defaultBuilder() {
        rudderConfigBuilder = new RudderConfig.Builder()
                .build();
        String toString = String.format(Locale.US, "RudderConfig: endPointUrl:%s | " +
                        "flushQueueSize: %d | dbCountThreshold: %d | sleepTimeOut: %d | logLevel: %d",
                rudderConfig.getDataPlaneUrl(),
                rudderConfig.getFlushQueueSize(),
                rudderConfig.getDbCountThreshold(),
                rudderConfig.getSleepTimeOut(),
                rudderConfig.getLogLevel());
        Assert.assertEquals(toString, rudderConfig.toString());
    }
}
