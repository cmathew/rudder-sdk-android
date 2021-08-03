package com.rudderstack.android.sdk.core;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rudderstack.android.sdk.core.util.RudderContextSerializer;
import com.rudderstack.android.sdk.core.util.RudderTraitsSerializer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import static org.robolectric.shadows.ShadowLog.getLogsForTag;

@RunWith(RobolectricTestRunner.class)
public class EventRepositoryTest {
    private EventRepository eventRepository;
    private Application application;
    private String writeKey = "mock_writeKey";
    @Mock private RudderConfig config;
    @Mock private RudderPreferenceManager rudderPreferenceManager;
    @Mock private RudderMessage rudderMessage;
    @Mock private Map<String, Object> mockMap;
    @Mock private Map<String, Object> mockMap2;
    @Mock private RudderOption defaultOptions;
    @Mock private RudderClient.Callback callback;
    @Mock private DBPersistentManager dbPersistentManager;
    private String key = "mock_key";
    private String anonymousId = "mock_anonymousId";
    private String advertisingId = "mock_advertisingId";
    private final String eventName = "mock_eventName";
    private int builderVersion = 1;
    private int defaultBuilderVersion = -1;
    private final String TAG = "RudderSDK";
    private final int versionCode = 0;
    private MockedStatic<RudderLogger> mockRudderLogger;
    private MockedStatic<RudderContext> mockRudderContext;
    private MockedStatic<RudderPreferenceManager> mockRudderPreferenceManager;
    private MockedStatic<RudderClient> mockRudderClient;
    private MockedStatic<DBPersistentManager> mockDBPersistentManager;
    private ArrayList<String> message;
    private HashSet<String> messageLog;

    @Before public void setup() {
        try {
            MockitoAnnotations.openMocks(this);
            application = ApplicationProvider.getApplicationContext();
            mockRudderLogger = Mockito.mockStatic(RudderLogger.class);
            mockRudderContext = Mockito.mockStatic(RudderContext.class);
            mockRudderPreferenceManager = Mockito.mockStatic(RudderPreferenceManager.class);
            mockRudderClient = Mockito.mockStatic(RudderClient.class);
            mockDBPersistentManager = Mockito.mockStatic(DBPersistentManager.class);

            mockDBPersistentManager.when(() -> DBPersistentManager.getInstance(application)).thenReturn(dbPersistentManager);

            // Mocking constructor:
            MockedConstruction<RudderContext> mockedRudderContextConstructor = Mockito.mockConstruction(RudderContext.class);
            MockedConstruction<RudderMessage> mockedRudderMessageConstructor = Mockito.mockConstruction(RudderMessage.class);

            // Setting anonymousId
            mockRudderContext.when(RudderContext::getAnonymousId).thenReturn(anonymousId);
            mockRudderPreferenceManager.when(() -> RudderPreferenceManager.getInstance(application)).thenReturn(rudderPreferenceManager);
            Mockito.when(config.isTrackLifecycleEvents()).thenReturn(true);

            // checkApplicationUpdateStatus()
            Mockito.when(rudderPreferenceManager.getBuildVersionCode()).thenReturn(builderVersion);

            message = new ArrayList<>();
            handleSetupLogMessage();
            // When buildVersion = 1
            message.add("Previous Installed Version: " + builderVersion);
            mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(10))).thenAnswer((Answer<Void>) invocation -> {
                Log.d(TAG, "Debug: " + message.get(10));
                return null;
            });

            // When previousVersionCode != versionCode
            message.add("Tracking Application Updated");
            mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(11))).thenAnswer((Answer<Void>) invocation -> {
                Log.d(TAG, "Debug: " + message.get(11));
                return null;
            });
            ShadowLog.reset();
            eventRepository = new EventRepository(application, writeKey, config, anonymousId, advertisingId);
            verifyDebugLogMessage();

            message = new ArrayList<>();
            handleSetupLogMessage();
            // checkApplicationUpdateStatus():
            // when buildVersion = -1
            Mockito.when(rudderPreferenceManager.getBuildVersionCode()).thenReturn(defaultBuilderVersion);
            message.add("Previous Installed Version: " + defaultBuilderVersion);
            mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(10))).thenAnswer((Answer<Void>) invocation -> {
                Log.d(TAG, "Debug: " + message.get(10));
                return null;
            });

            // When previousVersionCode == -1
            message.add("Tracking Application Installed");
            mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(11))).thenAnswer((Answer<Void>) invocation -> {
                Log.d(TAG, "Debug: " + message.get(11));
                return null;
            });
            ShadowLog.reset();
            eventRepository = new EventRepository(application, writeKey, config, anonymousId, advertisingId);
            verifyDebugLogMessage();

            // Closing constructor
            mockedRudderContextConstructor.close();
            mockedRudderMessageConstructor.close();
        }catch (UnsupportedEncodingException e) { }
    }

    @After public void setupComplete() {
        mockRudderLogger.close();
        mockRudderPreferenceManager.close();
        mockRudderContext.close();
        mockRudderClient.close();
        mockDBPersistentManager.close();
    }

    @Test public void setupTest() { Assert.assertTrue(true); }

    private void handleSetupLogMessage() throws UnsupportedEncodingException {
        message.add(String.format(Locale.US, "EventRepository: constructor: writeKey: %s", writeKey));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(0))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(0));
            return null;
        });

        String authHeaderString = Base64.encodeToString((String.format(Locale.US, "%s:", writeKey)).getBytes("UTF-8"), Base64.DEFAULT);
        message.add(String.format(Locale.US, "EventRepository: constructor: authHeaderString: %s", authHeaderString));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(1))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(1));
            return null;
        });

        message.add(String.format("EventRepository: constructor: %s", config.toString()));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(2))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(2));
            return null;
        });

        message.add("EventRepository: constructor: Initiating RudderElementCache");
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(3))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(3));
            return null;
        });

        message.add(String.format(Locale.US, "EventRepository: constructor: anonymousId: %s", anonymousId));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(4))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(4));
            return null;
        });

        String anonymousIdHeaderString = Base64.encodeToString(anonymousId.getBytes("UTF-8"), Base64.DEFAULT);
        message.add(String.format(Locale.US, "EventRepository: constructor: anonymousIdHeaderString: %s", anonymousIdHeaderString));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(5))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(5));
            return null;
        });

        message.add("EventRepository: constructor: Initiating DBPersistentManager");
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(6))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(6));
            return null;
        });

        message.add("EventRepository: constructor: Initiating RudderServerConfigManager");
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(7))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(7));
            return null;
        });

        // configManager constructor
        message.add("EventRepository: constructor: Initiating processor and factories");
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(8))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(8));
            return null;
        });

        message.add("Current Installed Version: " + versionCode);
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(9))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(9));
            return null;
        });
    }

    private void verifyDebugLogMessage() {
        messageLog = new HashSet<>();

        // Storing Shadow.log message
        for(int i = 0; i < getLogsForTag(TAG).size(); i++){
            messageLog.add(getLogsForTag(TAG).get(i).msg);
        }

        // Check if all the log message is present or not
        for(String msg : message){
            Assert.assertTrue(messageLog.contains("Debug: " + msg));
        }
    }

    @Test public void dump() {
        message = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RudderTraits.class, new RudderTraitsSerializer())
                .registerTypeAdapter(RudderContext.class, new RudderContextSerializer())
                .create();
        String eventJson = gson.toJson(rudderMessage);
        message.add(String.format(Locale.US, "EventRepository: dump: message: %s", eventJson));
        mockRudderLogger.when(() -> RudderLogger.logVerbose(message.get(0))).thenAnswer((Answer<Void>) invocation -> {
            Log.v(TAG, "Verbose: " + message.get(0));
            return null;
        });

        // When message.getIntegrations().size() == 0 and all true
        Mockito.when(rudderMessage.getIntegrations()).thenReturn(mockMap);
        mockRudderClient.when(RudderClient::getDefaultOptions).thenReturn(defaultOptions);
        Mockito.when(defaultOptions.getIntegrations()).thenReturn(mockMap2);
        Mockito.when(mockMap2.size()).thenReturn(1);

        Mockito.when(rudderMessage.getEventName()).thenReturn(eventName);
        message.add(String.format(Locale.US, "EventRepository: dump: eventName: %s", rudderMessage.getEventName()));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(1))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(1));
            return null;
        });

        // Since there is no way to initialise 'isFactoryInitialized', skipping makeFactoryDump()
        // makeFactoryDump() log message handling:
        message.add("EventRepository: makeFactoryDump: factories are not initialized. dumping to replay queue");
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(2))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(2));
            return null;
        });

        ShadowLog.reset();
        eventRepository.dump(rudderMessage);

        messageLog = new HashSet<>();
        // Storing Shadow.log message
        for(int i = 0; i < getLogsForTag(TAG).size(); i++){
            messageLog.add(getLogsForTag(TAG).get(i).msg);
        }
        Assert.assertTrue(messageLog.contains("Verbose: " + message.get(0)));
        ArrayList<String> messageCopy = new ArrayList<>(message);
        messageCopy.remove(0);
        // Check if all the log message is present or not
        for(String msg : messageCopy){
            Assert.assertTrue(messageLog.contains("Debug: " + msg));
        }

        /*
        To check dump method, 'else' case:
        else {
                message.setIntegrations(prepareIntegrations());
            }
         */
        // When message.getIntegrations().size() == 0 and RudderClient.getDefaultOptions() = null
        Mockito.when(rudderMessage.getIntegrations()).thenReturn(mockMap);
        mockRudderClient.when(RudderClient::getDefaultOptions).thenReturn(null);
        eventRepository.dump(rudderMessage);

        // When message.getIntegrations().size() == 0 and RudderClient.getDefaultOptions().getIntegrations() = null
        Mockito.when(rudderMessage.getIntegrations()).thenReturn(mockMap);
        mockRudderClient.when(RudderClient::getDefaultOptions).thenReturn(defaultOptions);
        Mockito.when(defaultOptions.getIntegrations()).thenReturn(null);
        eventRepository.dump(rudderMessage);

        // When message.getIntegrations().size() == 0 and RudderClient.getDefaultOptions().getIntegrations().size() = 0
        Mockito.when(rudderMessage.getIntegrations()).thenReturn(mockMap);
        mockRudderClient.when(RudderClient::getDefaultOptions).thenReturn(defaultOptions);
        Mockito.when(defaultOptions.getIntegrations()).thenReturn(mockMap2);
        Mockito.when(mockMap2.size()).thenReturn(0);
        eventRepository.dump(rudderMessage);
    }

    @Test public void onIntegrationReady() {
        message = new ArrayList<>();
        message.add(String.format(Locale.US, "EventRepository: onIntegrationReady: callback registered for %s", key));
        mockRudderLogger.when(() -> RudderLogger.logDebug(message.get(0))).thenAnswer((Answer<Void>) invocation -> {
            Log.d(TAG, "Debug: " + message.get(0));
            return null;
        });
        eventRepository.onIntegrationReady(key, callback);
        verifyDebugLogMessage();
    }
}
