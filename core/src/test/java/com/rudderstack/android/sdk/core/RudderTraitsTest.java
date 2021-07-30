package com.rudderstack.android.sdk.core;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RudderTraitsTest {
    private RudderTraits rudderTraits;
    @Mock private RudderTraits mockRudderTraits;
    private String anonymousId = "mock_anonymousId";
    @Mock private Date date;
    @Mock private RudderTraits.Address address;
    private String age = "mock_age";
    private String birthday = "mock_birthday";
    @Mock private RudderTraits.Company company;
    private String createdAt = "mock_createdAt";
    private String description = "mock_description";
    private String email = "mock_email";
    private String firstName = "mock_firstName";
    private String gender = "mock_gender";
    private String id = "mock_id";
    private String oldId = "mock_oldId";
    private String lastName = "mock_lastName";
    private String name = "mock_name";
    private String phone = "mock_phone";
    private String title = "mock_title";
    private String userName = "mock_userName";
    private String key = "mock_key";
    private String value = "mock_value";
    private Map<String, Object> extras;
    private Map<String, Object> traitsMap;

    private final String ANONYMOUSID_KEY = "anonymousId";
    private final String ADDRESS_KEY = "address";
    private final String AGE_KEY = "age";
    private final String BIRTHDAY_KEY = "birthday";
    private final String COMPANY_KEY = "company";
    private final String CREATEDAT_KEY = "createdat";
    private final String DESCRIPTION_KEY = "description";
    private final String EMAIL_KEY = "email";
    private final String FIRSTNAME_KEY = "firstname";
    private final String GENDER_KEY = "gender";
    private final String USERID_KEY = "userId";
    private final String LASTNAME_KEY = "lastname";
    private final String NAME_KEY = "name";
    private final String PHONE_KEY = "phone";
    private final String TITLE_KEY = "title";
    private final String USERNAME_KEY = "username";

    // Address
    private String city = "mock_city";
    private String country = "mock_country";
    private String postalCode = "mock_postalCode";
    private String state = "mock_state";
    private String street = "mock_street";

    private MockedStatic<RudderClient> mockRudderClient;
    private MockedStatic<RudderContext> mockRudderContext;
    private Application application;

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        application = ApplicationProvider.getApplicationContext();
        mockRudderClient = Mockito.mockStatic(RudderClient.class);
        mockRudderContext = Mockito.mockStatic(RudderContext.class);
        traitsMap = new HashMap<>();
        traitsMap.put(ADDRESS_KEY, address);
        traitsMap.put(ANONYMOUSID_KEY, anonymousId);
        traitsMap.put(AGE_KEY, age);
        traitsMap.put(BIRTHDAY_KEY, birthday);
        traitsMap.put(COMPANY_KEY, "mock_company");
        traitsMap.put(CREATEDAT_KEY, createdAt);
        traitsMap.put(DESCRIPTION_KEY, description);
        traitsMap.put(EMAIL_KEY, email);
        traitsMap.put(FIRSTNAME_KEY, firstName);
        traitsMap.put(GENDER_KEY, gender);
        traitsMap.put(USERID_KEY, id);
        traitsMap.put(LASTNAME_KEY, lastName);
        traitsMap.put(NAME_KEY, name);
        traitsMap.put(PHONE_KEY, phone);
        traitsMap.put(TITLE_KEY, title);
        traitsMap.put(USERNAME_KEY, userName);
    }

    @After public void setupComplete() {
        mockRudderClient.close();
        mockRudderContext.close();
    }

    @Test public void constructor() {
        mockRudderClient.when(RudderClient::getApplication).thenReturn(application);
        mockRudderContext.when(RudderContext::getAnonymousId).thenReturn(anonymousId);

        rudderTraits = new RudderTraits();
        Assert.assertNotNull(rudderTraits);
        rudderTraits = new RudderTraits(anonymousId);
        Assert.assertNotNull(rudderTraits);
        rudderTraits = new RudderTraits(address, age, birthday, company, createdAt, description, email, firstName, gender, id, lastName, name, phone, title, userName);
        Assert.assertNotNull(rudderTraits);
    }

    @Test public void getter() {
        assertEquals(anonymousId, RudderTraits.getAnonymousId(traitsMap));
        assertEquals(age, RudderTraits.getAge(traitsMap));
        assertEquals(birthday, RudderTraits.getBirthday(traitsMap));
        assertEquals("mock_company", RudderTraits.getCompany(traitsMap));
        assertEquals(createdAt, RudderTraits.getCreatedAt(traitsMap));
        assertEquals(description, RudderTraits.getDescription(traitsMap));
        assertEquals(email, RudderTraits.getEmail(traitsMap));
        assertEquals(firstName, RudderTraits.getFirstname(traitsMap));
        assertEquals(gender, RudderTraits.getGender(traitsMap));
        assertEquals(id, RudderTraits.getUserid(traitsMap));
        assertEquals(lastName, RudderTraits.getLastname(traitsMap));
        assertEquals(name, RudderTraits.getName(traitsMap));
        assertEquals(phone, RudderTraits.getPhone(traitsMap));
        assertEquals(title, RudderTraits.getTitle(traitsMap));
        assertEquals(userName, RudderTraits.getUsername(traitsMap));
    }

    @Test public void setter() {
        mockRudderTraits.putAddress(address);
        mockRudderTraits.putAge(age);
        mockRudderTraits.putBirthday(birthday);
        mockRudderTraits.putBirthday(date);
        mockRudderTraits.putCompany(company);
        mockRudderTraits.putCreatedAt(createdAt);
        mockRudderTraits.putDescription(description);
        mockRudderTraits.putEmail(email);
        mockRudderTraits.putFirstName(firstName);
        mockRudderTraits.putGender(gender);
        mockRudderTraits.putId(id);
        mockRudderTraits.putLastName(lastName);
        mockRudderTraits.putName(name);
        mockRudderTraits.putPhone(phone);
        mockRudderTraits.putTitle(title);
        mockRudderTraits.putUserName(userName);

        Mockito.verify(mockRudderTraits).putAddress(address);
        Mockito.verify(mockRudderTraits).putAge(age);
        Mockito.verify(mockRudderTraits).putBirthday(birthday);
        Mockito.verify(mockRudderTraits).putBirthday(date);
        Mockito.verify(mockRudderTraits).putCompany(company);
        Mockito.verify(mockRudderTraits).putCreatedAt(createdAt);
        Mockito.verify(mockRudderTraits).putDescription(description);
        Mockito.verify(mockRudderTraits).putEmail(email);
        Mockito.verify(mockRudderTraits).putFirstName(firstName);
        Mockito.verify(mockRudderTraits).putGender(gender);
        Mockito.verify(mockRudderTraits).putId(id);
        Mockito.verify(mockRudderTraits).putLastName(lastName);
        Mockito.verify(mockRudderTraits).putName(name);
        Mockito.verify(mockRudderTraits).putPhone(phone);
        Mockito.verify(mockRudderTraits).putTitle(title);
        Mockito.verify(mockRudderTraits).putUserName(userName);
    }

    @Test public void extra() {
        rudderTraits = new RudderTraits(anonymousId);
        rudderTraits.put(key, value);
        extras = new HashMap<>();
        extras.put(key, value);
        assertEquals(extras, rudderTraits.getExtras());
    }

    @Test public void AddressConstructor() {
        RudderTraits.Address address;

        // Default constructor
        address = new RudderTraits.Address();

        // Parameterised constructor
        address = new RudderTraits.Address(city, country, postalCode, state, street);
        assertEquals(city, address.getCity());
        assertEquals(country, address.getCountry());
        assertEquals(postalCode, address.getPostalCode());
        assertEquals(state, address.getState());
        assertEquals(street, address.getStreet());
    }

    @Test public void AddressPutCity() {
        RudderTraits.Address address = new RudderTraits.Address();
        address.putCity(city);
        assertEquals(city, address.getCity());
    }

    @Test public void AddressPutCountry() {
        RudderTraits.Address address = new RudderTraits.Address();
        address.putCountry(country);
        assertEquals(country, address.getCountry());
    }

    @Test public void AddressPutPostalCode() {
        RudderTraits.Address address = new RudderTraits.Address();
        address.putPostalCode(postalCode);
        assertEquals(postalCode, address.getPostalCode());
    }

    @Test public void AddressPutState() {
        RudderTraits.Address address = new RudderTraits.Address();
        address.putState(state);
        assertEquals(state, address.getState());
    }

    @Test public void AddressPutStreet() {
        RudderTraits.Address address = new RudderTraits.Address();
        address.putStreet(street);
        assertEquals(street, address.getStreet());
    }

    @Test public void AddressFromString() {
        String addressJSON = "{\n" +
                "          \"city\": \"Kolkata\",\n" +
                "          \"country\": \"India\",\n" +
                "          \"postalcode\": \"700096\",\n" +
                "          \"state\": \"West bengal\",\n" +
                "          \"street\": \"Park Street\"\n" +
                "        }";
        RudderTraits.Address address = RudderTraits.Address.fromString(addressJSON);
        assertEquals("Kolkata", address.getCity());
        assertEquals("India", address.getCountry());
        assertEquals("700096", address.getPostalCode());
        assertEquals("West bengal", address.getState());
        assertEquals("Park Street", address.getStreet());
    }
}
