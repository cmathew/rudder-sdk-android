package com.rudderstack.android.sdk.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.HashMap;
import java.util.Map;

public class RudderMessageTest {
    private RudderMessage rudderMessage;
    @Mock RudderContext rudderContext;
    @Mock RudderTraits groupTraits;
    @Mock RudderProperty property;
    @Mock RudderUserProperty userProperty;
    @Mock RudderOption rudderOption;
    private MockedStatic<RudderElementCache> mockRudderElementCache;
    private MockedStatic<RudderContext> mockRudderContext;
    String anonymousId;
    Map<String, Object> propertiesWithRandomData;

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        mockRudderElementCache = Mockito.mockStatic(RudderElementCache.class);
        mockRudderContext = Mockito.mockStatic(RudderContext.class);
        anonymousId = "mockAnonymousId";
        Map<String, Object> traitsWithId = new HashMap<>();
        traitsWithId.put("id","mockUserId");

        propertiesWithRandomData = new HashMap<>();
        propertiesWithRandomData.put("mock_key","mock_value");

        mockRudderElementCache.when(RudderElementCache::getCachedContext).thenReturn(rudderContext);
        mockRudderContext.when(RudderContext::getAnonymousId).thenReturn(anonymousId);
        Mockito.when(rudderContext.getTraits()).thenReturn(traitsWithId);
        rudderMessage = new RudderMessage();
    }

    @After public void complete() {
        mockRudderContext.close();
        mockRudderElementCache.close();
    }

    @Test public void testFun() {
        int i =6;
    }

    @Test public void init() {
        mockRudderElementCache.when(RudderElementCache::getCachedContext).thenReturn(rudderContext);
        mockRudderContext.when(RudderContext::getAnonymousId).thenReturn(anonymousId);
        Map<String, Object> traitsWithoutId = new HashMap<>();

        // When traits is null
        Mockito.when(rudderContext.getTraits()).thenReturn(null);
        rudderMessage = new RudderMessage();

        // WHen traits is not null but do not contain "id"
        Mockito.when(rudderContext.getTraits()).thenReturn(traitsWithoutId);
        rudderMessage = new RudderMessage();
    }

    @Test public void setter_getter() {
        String type = "mock_type";
        String userId = "mock_userId";
        String event = "mock_eventName";
        String previousId = "mock_previousId";
        String groupId = "mock_groupId";
        Map<String,Object> customContexts = new HashMap<>(propertiesWithRandomData);
        String integrationKey = "mock_integrationKey";
        Map props = new HashMap(propertiesWithRandomData);
        Map<String, Object> integrationsTrue = new HashMap<>();
        integrationsTrue.put(integrationKey, true);
        Map<String, Object> integrationsFalse = new HashMap<>();
        integrationsFalse.put(integrationKey, false);
        Map<String,Object> previousIntegration;
        RudderContext previousContext;


        rudderMessage.setPreviousId(previousId);
        // Assert.assertEquals(previousId, rudderMessage.get());

        rudderMessage.setGroupId(groupId);
        Assert.assertEquals(groupId, rudderMessage.getGroupId());

        rudderMessage.setGroupTraits(groupTraits);
        // Assert.assertEquals(, rudderMessage.getTraits());

        rudderMessage.setProperty(null);
        Assert.assertNull(rudderMessage.getProperties());

        Mockito.when(property.getMap()).thenReturn(propertiesWithRandomData);
        rudderMessage.setProperty(property);
        Assert.assertEquals(propertiesWithRandomData, rudderMessage.getProperties());

        Mockito.when(userProperty.getMap()).thenReturn(propertiesWithRandomData);
        rudderMessage.setUserProperty(userProperty);
        Assert.assertEquals(propertiesWithRandomData, rudderMessage.getUserProperties());

        rudderMessage.setType(type);
        Assert.assertEquals(type, rudderMessage.getType());

        rudderMessage.setUserId(userId);
        Assert.assertEquals(userId, rudderMessage.getUserId());

        rudderMessage.setEventName(event);
        Assert.assertEquals(event, rudderMessage.getEventName());

        rudderMessage.addIntegrationProps(integrationKey, false, props);
        Assert.assertEquals(integrationsFalse, rudderMessage.getIntegrations());

        rudderMessage.addIntegrationProps(integrationKey, true, props);
        Assert.assertEquals(integrationsTrue, rudderMessage.getIntegrations());
        // No method to retrieve 'destinationProps'

        previousIntegration = rudderMessage.getIntegrations();
        rudderMessage.setIntegrations(null);
        Assert.assertEquals(previousIntegration, rudderMessage.getIntegrations());

        previousIntegration = rudderMessage.getIntegrations();
        previousIntegration.putAll(propertiesWithRandomData);
        rudderMessage.setIntegrations(propertiesWithRandomData);
        Assert.assertEquals(previousIntegration, rudderMessage.getIntegrations());

        previousContext = rudderMessage.getContext();
        rudderMessage.setCustomContexts(null);
        Assert.assertEquals(previousContext, rudderMessage.getContext());

        rudderMessage.setCustomContexts(propertiesWithRandomData);
        // No method to retrieve 'customContexts'

        Assert.assertEquals(anonymousId, rudderMessage.getAnonymousId());

        rudderMessage.setRudderOption(null);
        Assert.assertNull(rudderMessage.getRudderOption());

        previousIntegration = rudderMessage.getIntegrations();
        previousIntegration.putAll(propertiesWithRandomData);
        Mockito.when(rudderOption.getIntegrations()).thenReturn(propertiesWithRandomData);
        Mockito.when(rudderOption.getCustomContexts()).thenReturn(customContexts);
        rudderMessage.setRudderOption(rudderOption);
        Assert.assertEquals(previousIntegration, rudderMessage.getIntegrations());
        Assert.assertEquals(rudderOption, rudderMessage.getRudderOption());

        mockRudderElementCache.when(RudderElementCache::getCachedContext).thenReturn(rudderContext);
        rudderMessage.updateContext();
        Assert.assertEquals(rudderContext, rudderMessage.getContext());
    }

}
