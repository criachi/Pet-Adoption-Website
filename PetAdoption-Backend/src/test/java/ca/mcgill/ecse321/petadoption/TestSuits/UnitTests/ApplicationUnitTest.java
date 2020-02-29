package ca.mcgill.ecse321.petadoption.TestSuits.UnitTests;

import ca.mcgill.ecse321.petadoption.TestSuits.Utils.TestUtils;
import ca.mcgill.ecse321.petadoption.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.petadoption.dao.AppUserRepository;
import ca.mcgill.ecse321.petadoption.dao.AdvertisementRepository;
import ca.mcgill.ecse321.petadoption.dao.ApplicationRepository;

import ca.mcgill.ecse321.petadoption.service.ApplicationService;

@ExtendWith(MockitoExtension.class)
public class ApplicationUnitTest { //application test service
    @Mock
    private ApplicationRepository applicationDao;

    @Mock
    private AppUserRepository appUserDao;

    @Mock
    private AdvertisementRepository advertisementDao;

    @InjectMocks
    private ApplicationService service;

    //3 users created: User 1 posts the advertisement while user 2 and 3 apply
    private static final String USER_NAME_1 = "user 1";
    private static final String USER_EMAIL_1 = "user1@mcgill.ca";
    private static final String USER_PASSWORD_1 = "password 1";
    private static final String USER_BIO_1 = "empty";
    private static final String USER_HOME_1 = "its nice";
    private static final Integer USER_AGE_1 = 34;
    private static final Sex USER_SEX_1 = Sex.M;
    private static final boolean USER_ADMIN_1 = true;

    private static final String USER_NAME_2 = "user 2";
    private static final String USER_EMAIL_2 = "user2@mcgill.ca";
    private static final String USER_PASSWORD_2 = "password 2";
    private static final String USER_BIO_2 = "empty_ish";
    private static final String USER_HOME_2 = "not so nice";
    private static final Integer USER_AGE_2 = 23;
    private static final Sex USER_SEX_2 = Sex.F;
    private static final boolean USER_ADMIN_2 = false;

    private static final String USER_NAME_3 = "user 3";
    private static final String USER_EMAIL_3 = "user3@gmail.com";
    private static final String USER_PASSWORD_3 = "password 3";
    private static final String USER_BIO_3 = "empty_ish";
    private static final String USER_HOME_3 = "cozy";
    private static final Integer USER_AGE_3 = 80;
    private static final Sex USER_SEX_3 = Sex.M;
    private static final boolean USER_ADMIN_3 = false;

    private static final AppUser user1 = TestUtils.createAppUser(USER_NAME_1, USER_EMAIL_1, USER_PASSWORD_1, USER_BIO_1,
            USER_HOME_1, USER_AGE_1, USER_ADMIN_1, USER_SEX_1);
    private static final AppUser user2 = TestUtils.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
            USER_HOME_2, USER_AGE_2, USER_ADMIN_2, USER_SEX_2);
    private static final AppUser user3 = TestUtils.createAppUser(USER_NAME_3, USER_EMAIL_3, USER_PASSWORD_3, USER_BIO_3,
            USER_HOME_3, USER_AGE_3, USER_ADMIN_3, USER_SEX_3);

    private static final Date datePosted = Date.valueOf("2020-02-19");
    private static final boolean isExpired = false;
    private Set<Application> applications;
    private static final AppUser postedBy = user1;
    private static final String petName = "cookie";
    private static final Integer petAge = 2;
    private static final String petDescription = "blablabla";
    private Set<Image> petImages;

    private static final Advertisement advertisement = TestUtils.createAdvertisement(datePosted, isExpired, postedBy, petName, petAge, petDescription);

    private static final String NOTE = "loves cats";
    private static final Date DATE_OF_SUBMISSION = Date.valueOf("2020-02-19");
    private static final Status STATUS = Status.pending;

    private static final String NOTE2 = "loves dogs";
    private static final Date DATE_OF_SUBMISSION2 = Date.valueOf("2020-02-20");
    private static final Status STATUS2 = Status.pending;

    private String error;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(applicationDao.save(any(Application.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (((Application) invocation.getArgument(0)).getDateOfSubmission().equals(DATE_OF_SUBMISSION)) {
                        return TestUtils.createApplication(advertisement, user2, DATE_OF_SUBMISSION, NOTE, STATUS);

                    } else if (((Application) invocation.getArgument(0)).getDateOfSubmission().equals(DATE_OF_SUBMISSION2)) {
                        return TestUtils.createApplication(advertisement, user3, DATE_OF_SUBMISSION2, NOTE2, STATUS2);
                    } else {
                        return null;
                    }
                }
        );

        lenient().when(applicationDao.findAll()).thenAnswer(
                (InvocationOnMock invocation) -> {
                    ArrayList<Application> arr = new ArrayList<Application>();
                    arr.add(TestUtils.createApplication(advertisement, user2, DATE_OF_SUBMISSION, NOTE, STATUS));
                    arr.add(TestUtils.createApplication(advertisement, user3, DATE_OF_SUBMISSION2, NOTE2, STATUS2));
                    return arr;
                }
        );

        lenient().when(appUserDao.findAppUserByEmail(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USER_EMAIL_2)) {
                        return TestUtils.createAppUser(USER_NAME_2, USER_EMAIL_2, USER_PASSWORD_2, USER_BIO_2,
                                USER_HOME_2, USER_AGE_2, USER_ADMIN_2, USER_SEX_2);
                    } else {
                        return null;
                    }
                }
        );
        lenient().when(advertisementDao.findAdvertisementByAdvertisementId(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(advertisement.getAdvertisementId())) {
                        return TestUtils.createAdvertisement(datePosted, isExpired, postedBy, petName, petAge, petDescription);
                    } else {
                        return null;
                    }
                }
        );
    }

    @Test
    public void createApplicationTest() {
        Application app = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
        }
        catch (IllegalArgumentException e){
            fail();
        }
        TestUtils.assertApplication(app, advertisement, user2, DATE_OF_SUBMISSION, NOTE, STATUS);
    }

    @Test
    public void duplicateApplicationsPerAdvertisement(){
        Application app = null;
        Application app2 = null;
        error = "";
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, NOTE, STATUS);
            app2 = service.createApplication(advertisement.getAdvertisementId(), user3.getEmail(), DATE_OF_SUBMISSION2, NOTE2, STATUS2);
        }
        catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals(error,"You already applied for this");
    }

    @Test
    public void applicationWithoutNote() {
        Application app = null;
        try {
            app = service.createApplication(advertisement.getAdvertisementId(), user2.getEmail(), DATE_OF_SUBMISSION, null, STATUS);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
    }
        assertEquals(error, "note cannot be empty " );
    }
}