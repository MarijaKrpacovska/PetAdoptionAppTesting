package mk.finki.ukim.milenichinja.UnitTests;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Models.Exceptions.DonationCauseCannotBeNullException;
import mk.finki.ukim.milenichinja.Models.Exceptions.DonationCauseNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.TransferSumInvalidException;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.DonationService;
import mk.finki.ukim.milenichinja.Service.Impl.DonationCauseServiceImpl;
import mk.finki.ukim.milenichinja.Service.Impl.DonationServiceImpl;
import mk.finki.ukim.milenichinja.Service.ValuteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DonationServiceTests {

    @Mock
    private DonationCauseRepository donationCauseRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private ValuteService valuteService;

    private DonationService service;

    private DonationCause donationCause1;

    private DonationCause donationCause2;

    private Donation donation;

    private List<Integer> petList;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        donationCause1 = new DonationCause("dc1","descr","url",600.00,400.00,null, Status.ACTIVE,1);
        donationCause2 = new DonationCause("dc1","descr","url",600.00,200.00,null, Status.ACTIVE,1);

        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);

        donation= new Donation(user,30, PaymentMethod.CREDITCARD,Integer.toUnsignedLong(10),ZonedDateTime.now(),new Valute("MKD","Mak","MKD",0),donationCause1);

        Mockito.when(this.donationRepository.save(Mockito.any(Donation.class))).thenReturn(donation);

        this.service = Mockito.spy(new DonationServiceImpl(donationRepository,donationCauseRepository));
    }

    //save
    @Test
    public void saveTest1() { //	F F F F F F
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);
        Donation d1 = this.service.save(user,30, PaymentMethod.CREDITCARD,Integer.toUnsignedLong(10),new Valute("MKD","Mak","MKD",0),donationCause1).get();
        Assert.assertNotNull(d1);
    }

    //save
    @Test
    public void saveTest2() { //	T F F F F F
        Donation d1 = this.service.save(null,30, PaymentMethod.CREDITCARD,Integer.toUnsignedLong(10),new Valute("MKD","Mak","MKD",0),donationCause1).get();
        Assert.assertNotNull(d1);
    }

    @Test
    public void saveTest3() { //	F T F F F F
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);
        Donation d1 = this.service.save(user,-10, PaymentMethod.CREDITCARD,Integer.toUnsignedLong(10),new Valute("MKD","Mak","MKD",0),donationCause1).get();
        Assert.assertNotNull(d1);
    }

    @Test
    public void saveTest4() { //	F F T F F F
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);
        Donation d1 = this.service.save(user,-10, null,Integer.toUnsignedLong(10),new Valute("MKD","Mak","MKD",0),donationCause1).get();
        Assert.assertNotNull(d1);
    }

    @Test
    public void saveTest5() { //	F F F T F F
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);
        Donation d1 = this.service.save(user,-10, PaymentMethod.CREDITCARD,Integer.toUnsignedLong(-100),new Valute("MKD","Mak","MKD",0),donationCause1).get();
        Assert.assertNotNull(d1);
    }

    @Test
    public void saveTest6() { //	F F F F T F
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);
        Donation d1 = this.service.save(user,-10, PaymentMethod.CREDITCARD,Integer.toUnsignedLong(-100),null,donationCause1).get();
        Assert.assertNotNull(d1);
    }

    @Test
    public void saveTest7() { //	F F F F F T
        Assert.assertThrows(
                DonationCauseCannotBeNullException.class,
                ()->this.service.save(new AppUser("username", "name", "surname","email@gmail.com","pass",
                        ZonedDateTime.now(), Role.ROLE_USER,City.Skopje),-10, PaymentMethod.CREDITCARD,
                        Integer.toUnsignedLong(-100),new Valute("MKD","Mak","MKD",0),
                        null));
    }

    //updateDCCurrentSum
    @Test
    public void updateDCCurrentSumTest1() { //	F F
        DonationCause dc = service.updateDCCurrentSum(donationCause1,100).get();
        Assert.assertEquals(dc.getCurrentSum(),500,0);
    }

    @Test
    public void updateDCCurrentSumTest2() { //	T F
        Assert.assertThrows(
                DonationCauseCannotBeNullException.class,
                ()->service.updateDCCurrentSum(null,100));
    }

    @Test
    public void updateDCCurrentSumTest3() { //	F T
        DonationCause dc = service.updateDCCurrentSum(donationCause1,-100).get();
        Assert.assertEquals(dc.getCurrentSum(),300,0);
    }
}
