package com.rudderstack.android.sdk.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RudderOptionTest {
    private RudderOption rudderOption;
    private List<Map<String, Object>> externalIds;
    Map<String, Object> externalId;
    private Map<String, Object> integrations;
    private Map<String, Object> customContexts;
    @Mock private RudderIntegration.Factory factory;
    @Mock private Map<String, Object> context;
    private final String type = "mock_type";
    private final String type2 = "mock_type2";
    private final String type3 = "mock_type3";
    private final String id = "mock_id";
    private final String id2 = "mock_id2";
    private final String id3 = "mock_id3";
    private final String key = "mock_key";
    private final String key2 = "mock_key2";

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        externalIds = new ArrayList<>();
        integrations = new HashMap<>();
        customContexts = new HashMap<>();
        // Note: As we don't have a proper way to access 'RudderElementCache.cachedContext'
        // so skipping this field.
        rudderOption = new RudderOption();
    }

    @Test public void testFun() {
        int i = 6;
    }

    @Test public void putExternalId() {

        // Skipping PreferenceManager code's
        rudderOption = rudderOption.putExternalId(type, id);
        externalId = new HashMap<>();
        externalId.put("type", type);
        externalId.put("id", id);
        externalIds.add(externalId);
        Assert.assertEquals(externalIds, rudderOption.getExternalIds());

        rudderOption = rudderOption.putExternalId(type2, id2);
        externalId = new HashMap<>();
        externalId.put("type", type2);
        externalId.put("id", id2);
        externalIds.add(externalId);
        Assert.assertEquals(externalIds, rudderOption.getExternalIds());

        externalId.put("id", id3);
        rudderOption = rudderOption.putExternalId(type2, id3);
        Assert.assertEquals(externalIds, rudderOption.getExternalIds());

    }

    @Test public void putIntegration() {
        rudderOption = rudderOption.putIntegration(type, true);
        integrations.put(type, true);
        Assert.assertEquals(integrations, rudderOption.getIntegrations());

        rudderOption = rudderOption.putIntegration(type2, false);
        integrations.put(type2, false);
        Assert.assertEquals(integrations, rudderOption.getIntegrations());

        rudderOption = rudderOption.putIntegration(type2, true);
        integrations.put(type2, true);
        Assert.assertEquals(integrations, rudderOption.getIntegrations());


        Mockito.when(factory.key()).thenReturn(type3);
        rudderOption = rudderOption.putIntegration(factory, false);
        integrations.put(type3, false);
        Assert.assertEquals(integrations, rudderOption.getIntegrations());

        rudderOption = rudderOption.putIntegration(factory, true);
        integrations.put(type3, true);
        Assert.assertEquals(integrations, rudderOption.getIntegrations());
    }

    @Test public void putCustomContext() {
        rudderOption = rudderOption.putCustomContext(key, context);
        customContexts.put(key, context);
        Assert.assertEquals(customContexts, rudderOption.getCustomContexts());

        rudderOption = rudderOption.putCustomContext(key2, context);
        customContexts.put(key2, context);
        Assert.assertEquals(customContexts, rudderOption.getCustomContexts());
    }
}
