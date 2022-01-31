package mk.finki.ukim.milenichinja.UnitTests;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.ValuteRepository;
import mk.finki.ukim.milenichinja.Service.DonationService;
import mk.finki.ukim.milenichinja.Service.Impl.DonationServiceImpl;
import mk.finki.ukim.milenichinja.Service.Impl.ValuteServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class ValuteServiceTests {
    @Mock
    private DonationCauseRepository donationCauseRepository;

    @Mock
    private ValuteRepository valuteRepository;

    @Mock
    private DonationRepository donationRepository;

    private ValuteService service;

    private Valute valute1;
    private Valute valute2;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        valute1 = new Valute("mkd","mkd","mkd",1);
        valute2 = new Valute("eur","eur","eur",60);

        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER, City.Skopje);

        //Mockito.when(this.donationRepository.save(Mockito.any(Donation.class))).thenReturn(donation);

        this.service = Mockito.spy(new ValuteServiceImpl(valuteRepository));
    }

    //Convert to MKD
    @Test
    public void ConvertToMKDTest1() {
        double returnValue = service.ConvertToMKD(100,valute2);
        Assert.assertEquals(returnValue,6000,0);
    }

    @Test
    public void ConvertToMKDTest2() {
        double returnValue = service.ConvertToMKD(-100,valute2);
        Assert.assertEquals(returnValue,-6000,0);
    }

    @Test
    public void ConvertToMKDTest3() {
        double returnValue = service.ConvertToMKD(0,valute2);
        Assert.assertEquals(returnValue,0,0);
    }

    @Test
    public void ConvertToMKDTest4() {
        double returnValue = service.ConvertToMKD(10,valute1);
        Assert.assertEquals(returnValue,10,0);
    }

}
