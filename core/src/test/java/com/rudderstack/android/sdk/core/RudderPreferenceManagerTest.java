package com.rudderstack.android.sdk.core;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RudderPreferenceManagerTest {
    private RudderPreferenceManager rudderPreferenceManager;
    private Application application;
    private final String configJson = "mock_configJson";
    private final String traitsJson = "mock_traitsJson";
    private final int versionCode = 100;
    private final String externalIdsJson = "mock_externalIdsJson";

    @Before public void setup() {
        application = ApplicationProvider.getApplicationContext();
        rudderPreferenceManager = RudderPreferenceManager.getInstance(application);
    }

    @Test public void updateLastUpdatedTime() {
        Assert.assertEquals(-1, rudderPreferenceManager.getLastUpdatedTime());
        rudderPreferenceManager.updateLastUpdatedTime();
        // No way to Assert on Time, hence implementing different logic
        Assert.assertNotEquals(-1, rudderPreferenceManager.getLastUpdatedTime());
    }

    @Test public void saveConfigJso() {
        Assert.assertNull(rudderPreferenceManager.getConfigJson());
        rudderPreferenceManager.saveConfigJson(configJson);
        Assert.assertEquals(configJson, rudderPreferenceManager.getConfigJson());
    }

    @Test public void saveTraits() {
        Assert.assertNull(rudderPreferenceManager.getTraits());
        rudderPreferenceManager.saveTraits(traitsJson);
        Assert.assertEquals(traitsJson, rudderPreferenceManager.getTraits());
    }

    @Test public void saveBuildVersionCode() {
        Assert.assertEquals(-1, rudderPreferenceManager.getBuildVersionCode());
        rudderPreferenceManager.saveBuildVersionCode(versionCode);
        Assert.assertEquals(versionCode, rudderPreferenceManager.getBuildVersionCode());
    }

    @Test public void saveExternalIds() {
        Assert.assertNull(rudderPreferenceManager.getExternalIds());
        rudderPreferenceManager.saveExternalIds(externalIdsJson);
        Assert.assertEquals(externalIdsJson, rudderPreferenceManager.getExternalIds());
    }

    @Test public void clearExternalIds() {
        // Save externalIdsJson
        rudderPreferenceManager.saveExternalIds(externalIdsJson);
        Assert.assertEquals(externalIdsJson, rudderPreferenceManager.getExternalIds());

        // Clear externalIdsJson
        rudderPreferenceManager.clearExternalIds();
        Assert.assertNull(rudderPreferenceManager.getExternalIds());
    }
}
