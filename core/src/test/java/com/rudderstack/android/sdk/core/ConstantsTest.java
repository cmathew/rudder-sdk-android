package com.rudderstack.android.sdk.core;

import org.junit.Assert;
import org.junit.Test;

public class ConstantsTest {
    private final int CONFIG_REFRESH_INTERVAL = 2;
    private final String DATA_PLANE_URL = "https://hosted.rudderlabs.com";
    private final int FLUSH_QUEUE_SIZE = 30;
    private final int DB_COUNT_THRESHOLD = 10000;
    private final int SLEEP_TIMEOUT = 10;
    private final String CONTROL_PLANE_URL = "https://api.rudderlabs.com";
    private final boolean TRACK_LIFECYCLE_EVENTS = true;
    private final boolean RECORD_SCREEN_VIEWS = false;

    @Test public void Constants() {
        Assert.assertEquals(CONFIG_REFRESH_INTERVAL, Constants.CONFIG_REFRESH_INTERVAL);
        Assert.assertEquals(DATA_PLANE_URL, Constants.DATA_PLANE_URL);
        Assert.assertEquals(FLUSH_QUEUE_SIZE, Constants.FLUSH_QUEUE_SIZE);
        Assert.assertEquals(DB_COUNT_THRESHOLD, Constants.DB_COUNT_THRESHOLD);
        Assert.assertEquals(SLEEP_TIMEOUT, Constants.SLEEP_TIMEOUT);
        Assert.assertEquals(CONTROL_PLANE_URL, Constants.CONTROL_PLANE_URL);
        Assert.assertEquals(TRACK_LIFECYCLE_EVENTS, Constants.TRACK_LIFECYCLE_EVENTS);
        Assert.assertEquals(RECORD_SCREEN_VIEWS, Constants.RECORD_SCREEN_VIEWS);
    }
}
