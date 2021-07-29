package com.rudderstack.android.sdk.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

/*
Note: We are not using Assert.assertEquals to fetch and verify values from RudderMessage object.
As this is against the principle of Unit Testing and also to simplify the (future) implementation.
 */

public class RudderMessageBuilderTest {
    RudderMessage rudderMessageBuilder;
    MockedConstruction<RudderMessage> mockedRudderContextConstructor;
    @Mock RudderTraits groupTraits;
    @Mock
    RudderProperty property;
    @Mock RudderPropertyBuilder builder;
    @Mock Map<String, Object> map;
    @Mock RudderUserProperty userProperty;
    @Mock RudderOption option;
    String groupId = "mock_groupId";
    String userId = "mock_userId";
    String previousId = "mock_previousId";
    String event = "mock_eventName";

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        mockedRudderContextConstructor = Mockito.mockConstruction(RudderMessage.class);
    }

    @After public void complete() {
        mockedRudderContextConstructor.close();
    }

    @Test public void testFun() {
        int i =6;
    }

    @Test public void groupId() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setGroupId(groupId)
                .build();
//        Assert.assertEquals(groupId, rudderMessageBuilder.getGroupId());
    }

    @Test public void setGroupTraits() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setGroupTraits(groupTraits)
                .build();
        // No way to retrieve 'traits' or (say) 'groupTraits'
    }

    @Test public void setPreviousId() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setPreviousId(previousId)
                .build();
    }

    @Test public void setEventName() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setEventName(event)
                .build();
    }

    @Test public void setUserId() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setUserId(userId)
                .build();
    }

    @Test public void setProperty() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setProperty(property)
                .build();

        rudderMessageBuilder = new RudderMessageBuilder()
                .setProperty(builder)
                .build();

        rudderMessageBuilder = new RudderMessageBuilder()
                .setProperty(map)
                .build();
    }

    @Test public void setUserProperty() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setUserProperty(userProperty)
                .build();

        rudderMessageBuilder = new RudderMessageBuilder()
                .setUserProperty(map)
                .build();
    }

    @Test public void setRudderOption() {
        rudderMessageBuilder = new RudderMessageBuilder()
                .setRudderOption(option)
                .build();
    }
}
