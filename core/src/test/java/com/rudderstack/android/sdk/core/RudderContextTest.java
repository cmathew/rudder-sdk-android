package com.rudderstack.android.sdk.core;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rudderstack.android.sdk.core.util.RudderTraitsSerializer;
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
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.robolectric.shadows.ShadowLog.getLogsForTag;

@RunWith(RobolectricTestRunner.class)
public class RudderContextTest {
    private RudderContext rudderContext_withId;
    private RudderContext rudderContext_withoutId;
    Application application;
    @Mock RudderPreferenceManager preferenceManger;
    RudderPreferenceManager preferenceManagerRealObject;
    String anonymousId = "mocked_anonymousId";
    String advertisingId = "mocked_AdvertisingId";
    List<Map<String, Object>> externalId;
    MockedStatic<RudderPreferenceManager> mockRudderPreferenceManager;
    MockedStatic<RudderClient> mockGetApplication;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        application = ApplicationProvider.getApplicationContext();
        preferenceManagerRealObject = RudderPreferenceManager.getInstance(application);
        mockRudderPreferenceManager = Mockito.mockStatic(RudderPreferenceManager.class);
        mockGetApplication = Mockito.mockStatic(RudderClient.class);


        String traitsJson_withId = "{\"anonymousId\":\"1b0f54ee6c892f5a\",\"userId\":\"mock_userId\",\"id\":\"mock_Id\"}";
        externalId = new ArrayList<>();
        Map<String, Object> listOfIds = new HashMap<>();
        listOfIds.put("user1", "mock_1");
        listOfIds.put("user2", "mock_2");
        listOfIds.put("user3", "mock_3");
        listOfIds.put("user4", "mock_4");
        externalId.add(listOfIds);


        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(preferenceManger);
        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);

        when(preferenceManger.getTraits()).thenReturn(traitsJson_withId);
        rudderContext_withId = new RudderContext(application, anonymousId, advertisingId);

        String traitsJson_withoutId = "{\"anonymousId\":\"1b0f54ee6c892f5a\",\"userId\":\"AdobeFinalTesting\"}";
        when(preferenceManger.getTraits()).thenReturn(traitsJson_withoutId);
        rudderContext_withoutId = new RudderContext(application, anonymousId, advertisingId);
    }

    @After
    public void complete() {
        mockRudderPreferenceManager.close();
        mockGetApplication.close();
    }

//    @Before
//    public void setup_withoutId() {
//        MockitoAnnotations.openMocks(this);
//        application = ApplicationProvider.getApplicationContext();
//        String traitsJson_withoutId = "{\"anonymousId\":\"1b0f54ee6c892f5a\",\"userId\":\"AdobeFinalTesting\"}";
//
//        MockedStatic<RudderPreferenceManager> mockRudderPreferenceManager = Mockito.mockStatic(RudderPreferenceManager.class);
//        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(preferenceManger);
//
//        MockedStatic<RudderClient> mockGetApplication = Mockito.mockStatic(RudderClient.class);
//        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);
//
//        when(preferenceManger.getTraits()).thenReturn(traitsJson_withoutId);
//        rudderContext_withoutId = new RudderContext(application, anonymousId, advertisingId);
//
//        mockRudderPreferenceManager.close();
//        mockGetApplication.close();
//    }

    @Test
    public void testFun(){
        Assert.assertTrue(true);
    }

    @Test
    public void resetTraits() {
        Assert.assertEquals(3, rudderContext_withId.getTraits().size());
        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);
        rudderContext_withId.resetTraits();
        Assert.assertEquals(1, rudderContext_withId.getTraits().size());
        mockGetApplication.when(RudderClient::getApplication).thenReturn(null);
        rudderContext_withId.resetTraits();
        Assert.assertEquals(0, rudderContext_withId.getTraits().size());
    }

    @Test
    public void updateTraits() {
        RudderTraits traits;
        int expectedTraitsSize;
        // When traits is null
        expectedTraitsSize = rudderContext_withId.getTraits().size();
        rudderContext_withId.updateTraits(null);
        Assert.assertEquals(expectedTraitsSize, rudderContext_withId.getTraits().size());

        // When existingId is not null
        traits = getRudderTraits();
        // From expectedTraitsSize '1' is subtracted because 'anonymousId' is present twice
        expectedTraitsSize = rudderContext_withoutId.getTraits().size() + getTraitsSize(traits) - 1;
        rudderContext_withoutId.updateTraits(traits);
        Assert.assertEquals(expectedTraitsSize,rudderContext_withoutId.getTraits().size());

        // When existingId is not null and newId is null;
        expectedTraitsSize = rudderContext_withId.getTraits().size();
        rudderContext_withId.updateTraits(null);
        Assert.assertEquals(expectedTraitsSize,rudderContext_withId.getTraits().size());

        // When existingId and newId is not null and existingId is not Equal to newId
        traits = new RudderTraits();
        traits.putId("mock_newId");
        expectedTraitsSize = getTraitsSize(traits);
        rudderContext_withId.updateTraits(traits);
        Assert.assertEquals(expectedTraitsSize, rudderContext_withId.getTraits().size());

        // When existingId and newId is not null and existingId is Equal to newId
        traits = new RudderTraits();
        traits.putId("mock_newId");
        expectedTraitsSize = getTraitsSize(traits);
        rudderContext_withId.updateTraits(traits);
        Assert.assertEquals(expectedTraitsSize, rudderContext_withId.getTraits().size());

        // When existingId and newId is not null and existingId is Equal to newId and traits is null
        expectedTraitsSize = rudderContext_withId.getTraits().size();
        rudderContext_withId.updateTraits(null);
        Assert.assertEquals(expectedTraitsSize,rudderContext_withId.getTraits().size());
    }

    @Test public void persistTraits() {
        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);
        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(RudderClient.getApplication())).thenReturn(preferenceManagerRealObject);
        String persistTraits = new Gson().toJson(rudderContext_withId.getTraits());
        rudderContext_withId.persistTraits();
        Assert.assertEquals(persistTraits, preferenceManagerRealObject.getTraits());
    }

    @Test public void updateTraitsMap() {
        Map<String, Object> traits;
        rudderContext_withId.updateTraitsMap(null);
        Assert.assertNull(rudderContext_withId.getTraits());

        traits = new HashMap<>();
        traits.put("userId", "mock_userId");
        traits.put("anonymousId", "mock_anonymousId");
        rudderContext_withId.updateTraitsMap(traits);
        Assert.assertEquals(traits, rudderContext_withId.getTraits());
    }

    /* We don't have get deviceToken
    @Test public void putDeviceToken() {
        String token = "mock_token";
        rudderContext_withId.putDeviceToken(token);
    }
    */

    @Test public void updateWithAdvertisingId() {
        String advertisingId;

        // Case 1: When advertisingId is null
        advertisingId = null;
        rudderContext_withId.updateWithAdvertisingId(advertisingId);
        Assert.assertFalse(rudderContext_withId.isAdTrackingEnabled());

        // Case 2: When advertisingId is empty
        advertisingId = "";
        rudderContext_withId.updateWithAdvertisingId(advertisingId);
        Assert.assertFalse(rudderContext_withId.isAdTrackingEnabled());

        // Case 3: When advertisingId is not null
        advertisingId = "mocked_newAdvertisingId";
        rudderContext_withId.updateWithAdvertisingId(advertisingId);
        Assert.assertTrue(rudderContext_withId.isAdTrackingEnabled());
        Assert.assertEquals(advertisingId, rudderContext_withId.getAdvertisingId());
    }

    // Not fully implemented, corresponding method uses thread class and private methods
    // Private methods could be implemented using PowerMockito
    @Test
    public void updateDeviceWithAdId() {
        MockedStatic<Utils> mockUtils = Mockito.mockStatic(Utils.class);
        ShadowLog.reset();
        String message = "Not collecting advertising ID because "
                + "com.google.android.gms.ads.identifier.AdvertisingIdClient "
                + "was not found on the classpath.";
        String TAG = "RudderSDK";
        MockedStatic<RudderLogger> mockRudderLogger = Mockito.mockStatic(RudderLogger.class);
        mockRudderLogger.when(() -> RudderLogger.logDebug(message)).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message);
            return null;
        });

        mockUtils.when(() -> Utils.isOnClassPath("com.google.android.gms.ads.identifier.AdvertisingIdClient")).thenReturn(false);
        rudderContext_withId.updateDeviceWithAdId();
        Assert.assertNotNull(ShadowLog.getLogsForTag(TAG));
        Assert.assertEquals(TAG, (getLogsForTag(TAG).get(0)).tag);
        Assert.assertEquals("Debug: " + message, (getLogsForTag(TAG).get(0)).msg);

        // Need to work on multi-thread for unit test cases
//        mockUtils.when(() -> Utils.isOnClassPath("com.google.android.gms.ads.identifier.AdvertisingIdClient")).thenReturn(true);
//        rudderContext_withId.updateDeviceWithAdId();

        mockRudderLogger.close();
    }

    @Test
    public void updateExternalIds() {
        application = ApplicationProvider.getApplicationContext();

        // When application, RudderPreferenceManager and externalId is Null
        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(null);
        mockGetApplication.when(RudderClient::getApplication).thenReturn(null);
        rudderContext_withId.updateExternalIds(null);
        Assert.assertNull(rudderContext_withId.getExternalIds());

        // When application, RudderPreferenceManager is not null and externalId is Null
        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(preferenceManagerRealObject);
        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);
        rudderContext_withId.updateExternalIds(null);
        Assert.assertNull(rudderContext_withId.getExternalIds());
        Assert.assertNull(preferenceManger.getExternalIds());

        // When application, RudderPreferenceManager is null and externalId is not Null
        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(null);
        mockGetApplication.when(RudderClient::getApplication).thenReturn(null);
        rudderContext_withId.updateExternalIds(externalId);
        Assert.assertEquals(externalId, rudderContext_withId.getExternalIds());

        // When application, RudderPreferenceManager is not null and externalId is Null
        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);
        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(preferenceManagerRealObject);
        rudderContext_withId.updateExternalIds(null);
        Assert.assertNull(rudderContext_withId.getExternalIds());
        Assert.assertNull(preferenceManagerRealObject.getExternalIds());

        // When application, RudderPreferenceManager and externalId is not null
        mockGetApplication.when(RudderClient::getApplication).thenReturn(application);
        mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(preferenceManagerRealObject);
        String expectedExternalId = new Gson().toJson(externalId);
        rudderContext_withId.updateExternalIds(externalId);
        Assert.assertNotNull(rudderContext_withId.getExternalIds());
        Assert.assertEquals(externalId, rudderContext_withId.getExternalIds());
        Assert.assertEquals(expectedExternalId, preferenceManagerRealObject.getExternalIds());
    }

    @Test public void setCustomContexts() {
        Map<String, Object> customContexts = new HashMap<>();
        customContexts.put("mock1", "value1");
        customContexts.put("mock2", "value2");
        customContexts.put("mock3", 123);
        customContexts.put("mock4", 123.4);

        // When customContext
        rudderContext_withId.setCustomContexts(null);
        Assert.assertNull(rudderContext_withId.customContextMap);

        // When this.customContextMap == null and customContext is not null
        rudderContext_withId.customContextMap = null;
        rudderContext_withId.setCustomContexts(customContexts);
        Assert.assertEquals(customContexts, rudderContext_withId.customContextMap);

        // When this.customContextMap and customContext is not null
        rudderContext_withId.customContextMap = customContexts;
        rudderContext_withId.setCustomContexts(customContexts);
        Assert.assertEquals(customContexts, rudderContext_withId.customContextMap);
    }

    @Test public void copy() {
        RudderContext copyRudderContext;

        // When traits and externalId is not null
        rudderContext_withId.updateExternalIds(externalId);
        copyRudderContext = rudderContext_withId.copy();
        Assert.assertTrue(new ReflectionEquals(rudderContext_withId).matches(copyRudderContext));

        // When traits and externalId is null
        rudderContext_withId.updateExternalIds(null);
        rudderContext_withId.updateTraitsMap(null);
        copyRudderContext = rudderContext_withId.copy();
        Assert.assertTrue(new ReflectionEquals(rudderContext_withId).matches(copyRudderContext));
    }

    // Should be moved to Utils class
    private RudderTraits getRudderTraits() {
        RudderTraits traits = new RudderTraits();
        traits.putBirthday(new Date());
        traits.putEmail("abc@123.com");
        traits.putFirstName("First");
        traits.putLastName("Last");
        traits.putGender("m");
        traits.putPhone("5555555555");

        RudderTraits.Address address = new RudderTraits.Address();
        address.putCity("City");
        address.putCountry("USA");
        traits.putAddress(address);

        traits.put("boolean", Boolean.TRUE);
        traits.put("integer", 50);
        traits.put("float", 120.4f);
        traits.put("long", 1234L);
        traits.put("string", "hello");
        traits.put("date", new Date(System.currentTimeMillis()));

        return traits;
    }

    // Move to Utils class
    private int getTraitsSize(RudderTraits traits) {
        // convert the whole traits to map and take care of the extras
        Gson gson = new GsonBuilder().registerTypeAdapter(RudderTraits.class, new RudderTraitsSerializer()).create();
        Map<String, Object> traitsMap = Utils.convertToMap(gson.toJson(traits));
        return traitsMap.size();
    }
}