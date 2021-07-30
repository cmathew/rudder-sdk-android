package com.rudderstack.android.sdk.core;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RudderTraitsBuilderTest {
    private Application application;
    private MockedStatic<RudderClient> mockRudderClient;

    private String city = "mock_city";
    private String country = "mock_country";
    private String postalCode = "mock_postalCode";
    private String state = "mock_state";
    private String street = "mock_street";
    private int age  = 20;
    private String birthDay = "mock_birthDay";
    private String companyName = "mock_companyName";
    private String companyId = "mock_companyId";
    private String industry = "mock_industry";
    private String createdAt = "mock_createdAt";
    private String description = "mock_description";
    private String email = "mock_email";
    private String firstName = "mock_firstName";
    private String gender = "mock_gender";
    private String id = "mock_id";
    private String lastName = "mock_lastName";
    private String name = "mock_name";
    private String phone = "mock_phone";
    private String title = "mock_title";
    private String userName = "mock_userName";

    @Before public void setup() {
        MockitoAnnotations.openMocks(this);
        application = ApplicationProvider.getApplicationContext();
        mockRudderClient = Mockito.mockStatic(RudderClient.class);
    }

    @After public void setupComplete() {
        mockRudderClient.close();
    }

    @Test public void setupTest() {
        mockRudderClient.when(RudderClient::getApplication).thenReturn(application);
        RudderTraits rudderTraits = new RudderTraitsBuilder()
                .setAge(age)
                .setBirthDay(birthDay)
                .setCity(city)
                .setCompanyId(companyId)
                .setCompanyName(companyName)
                .setCountry(country)
                .setCreateAt(createdAt)
                .setDescription(description)
                .setEmail(email)
                .setFirstName(firstName)
                .setGender(gender)
                .setId(id)
                .setIndustry(industry)
                .setLastName(lastName)
                .setName(name)
                .setPhone(phone)
                .setPostalCode(postalCode)
                .setState(state)
                .setStreet(street)
                .setTitle(title)
                .setUserName(userName)
                .build();

        Assert.assertNotNull(rudderTraits);
    }
}
