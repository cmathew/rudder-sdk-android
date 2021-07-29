package com.rudderstack.android.sdk.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class RudderPropertyTest {
    private RudderProperty rudderProperty;
    private Map<String, Object> map;
    private final String key = "mock_key";
    private final String key2 = "mock_key2";
    private final String key3 = "mock_key3";
    private final Object value2 = "mock_value2";
    private final double revenue = 50;
    private final String currency = "mock_currency";
    private Map<String, Object> map2;
    @Mock private Object value;
    @Mock private RudderProperty rudderPropertyValue;

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        map = new HashMap<>();
        map2 = new HashMap<>();
        rudderProperty = new RudderProperty();
    }

    @Test public void testFun() {
        int i = 6;
    }

    @Test public void put_get_has() {
        Assert.assertNull(rudderProperty.getProperty(key));
        Assert.assertFalse(rudderProperty.hasProperty(key));
        rudderProperty.put(key, value);
        Assert.assertTrue(rudderProperty.hasProperty(key));
        Assert.assertEquals(value, rudderProperty.getProperty(key));
    }

    @Test public void putValue() {
        rudderProperty = rudderProperty.putValue(key, rudderPropertyValue);
        map.put(key, rudderPropertyValue.getMap());
        Assert.assertEquals(map, rudderProperty.getMap());

        rudderProperty = rudderProperty.putValue(key2, value);
        map.put(key2, value);
        Assert.assertEquals(map, rudderProperty.getMap());


        map2.put(key3, value2);
        rudderProperty = rudderProperty.putValue(map2);
        map.putAll(map2);
        Assert.assertEquals(map, rudderProperty.getMap());

        rudderProperty = rudderProperty.putValue(null);
        Assert.assertEquals(map, rudderProperty.getMap());
    }

    @Test public void putRevenue() {
        rudderProperty.putRevenue(revenue);
        Assert.assertTrue(rudderProperty.hasProperty("revenue"));
        Assert.assertEquals(revenue, rudderProperty.getProperty("revenue"));
    }

    @Test public void putCurrency() {
        rudderProperty.putCurrency(currency);
        Assert.assertTrue(rudderProperty.hasProperty("currency"));
        Assert.assertEquals(currency, rudderProperty.getProperty("currency"));
    }
}
