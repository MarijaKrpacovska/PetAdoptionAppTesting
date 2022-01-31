package mk.finki.ukim.milenichinja.UnitTests;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Exceptions.CenterNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUsernameOrPasswordException;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Repository.Jpa.AppUserRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.CenterRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.Impl.AppUserServiceImpl;
import mk.finki.ukim.milenichinja.Service.Impl.CenterServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CenterServiceTests {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private CenterRepository centerRepository;

    private AppUser admin;
    private Center center;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CenterServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER, City.Skopje);
        admin = new AppUser("admin", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_ADMIN,City.Skopje);

        List<Center> centersList = new ArrayList<>();

        center = new Center("adr",City.Skopje,"url");

        Mockito.when(this.centerRepository.save(Mockito.any(Center.class))).thenReturn(center);
        Mockito.when(this.centerRepository.findById(0)).thenReturn(Optional.of(center));

        this.service = Mockito.spy(new CenterServiceImpl(this.centerRepository,this.petRepository));
    }

    //save
    @Test
    public void saveTest1() { //	F F F
        Center c1 = this.service.save("adr",City.Skopje,"url").get();
        Assert.assertNotNull(c1);
    }

    @Test
    public void saveTest2() { //	T F F
        Center c1 = this.service.save("",City.Skopje,"url").get();
        Assert.assertNotNull(c1);
    }

    @Test
    public void saveTest3() { //	F T F
        Center c1 = this.service.save("addr",null,"url").get();
        Assert.assertNotNull(c1);
    }

    @Test
    public void saveTest4() { //	F F T
        Center c1 = this.service.save("addr",City.Skopje,"").get();
        Assert.assertNotNull(c1);
    }

    //findById
    @Test
    public void findByIdTest1() { //	T
        Center c1 = this.service.findById(0).get();
        Assert.assertEquals(c1,center);
    }

    @Test
    public void findByIdTest2() { //	F
        Assert.assertThrows(
                NoSuchElementException.class,
                () -> this.service.findById(-1).get()
        );
    }

    //edit
    @Test
    public void editTest1() { //	T F F F
        Center c1 = this.service.edit(0,"adresa",City.Skopje,"url").get();
        Assert.assertEquals(c1.getAddress(),"adresa");
        Assert.assertEquals(c1.getCity(),City.Skopje);
        Assert.assertEquals(c1.getUrl(),"url");
    }

    @Test
    public void editTest2() { //	F F F F
        Assert.assertThrows(
                CenterNotFoundException.class,
                () -> this.service.edit(-1,"adresa",City.Skopje,"url")
        );
    }

    @Test
    public void editTest3() { //	T T F F
        Center c1 = this.service.edit(0,"",City.Skopje,"url").get();
        Assert.assertEquals(c1.getAddress(),"");
        Assert.assertEquals(c1.getCity(),City.Skopje);
        Assert.assertEquals(c1.getUrl(),"url");
    }

    @Test
    public void editTest4() { //	T F T F
        Center c1 = this.service.edit(0,"adresa",null,"url").get();
        Assert.assertEquals(c1.getAddress(),"adresa");
        Assert.assertNull(c1.getCity());
        Assert.assertEquals(c1.getUrl(),"url");
    }

    @Test
    public void editTest5() { //	T F F T
        Center c1 = this.service.edit(0,"adresa",City.Skopje,"").get();
        Assert.assertEquals(c1.getAddress(),"adresa");
        Assert.assertEquals(c1.getCity(), City.Skopje);
        Assert.assertEquals(c1.getUrl(),"");
    }

}
