package mk.finki.ukim.milenichinja.Selenium;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Service.AdoptionService;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.CenterService;
import mk.finki.ukim.milenichinja.Service.PetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumDonationCausesTesting {
    @Autowired
    AppUserService appUserService;

    @Autowired
    PetService petService;

    @Autowired
    CenterService centerService;

    @Autowired
    AdoptionService adoptionService;


    private WebDriver driver;

    private static Pet p1;
    private static AppUser regularUser;
    private static AppUser adminUser;

    private static boolean dataInitialized = false;

    @BeforeEach
    private void setup() {
        this.driver = getDriver();
        initData();
    }

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
        return new ChromeDriver();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }

    }

    private void initData() {
        if (!dataInitialized) {

            regularUser = appUserService.registerUser("u1","user1","user1",
                    City.Bitola,"u1@gmail.com","pass","pass", Role.ROLE_USER);
            adminUser = appUserService.registerUser("u2","user2","user2",
                    City.Bitola,"u2@gmail.com","pass","pass", Role.ROLE_ADMIN);

            //p1 = petService.save("p1",Type.DOG,"p1",Gender.FEMALE,"descr",c1.getId(),"url",adminUser,"2020-04-04").get();

            dataInitialized = true;
        }
    }
}
