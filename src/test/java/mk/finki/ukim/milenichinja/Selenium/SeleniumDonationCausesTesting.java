package mk.finki.ukim.milenichinja.Selenium;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Selenium.Pages.DonationCausesPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.DonationPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.LoginPage;
import mk.finki.ukim.milenichinja.Service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumDonationCausesTesting {
    @Autowired
    AppUserService appUserService;

    @Autowired
    CenterService centerService;

    @Autowired
    PetService petService;

    @Autowired
    DonationCauseService donationCauseService;

    @Autowired
    DonationService donationService;

    @Autowired
    ValuteService valuteService;


    private WebDriver driver;

    private static DonationCause dc1;
    private static DonationCause dc2;
    private static Valute valute;
    private static AppUser regularUser;
    private static AppUser adminUser;
    private static Center center;
    private static Pet p1;
    List<Integer> l1 = new ArrayList<>();

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

            center=centerService.save("addr",City.Skopje,"url").get();

            dc1 = donationCauseService.save("des","url",l1,10.0,"name",10).get();
            dc2 = donationCauseService.save("des","url",l1,10.0,"name",10).get();

            valute=valuteService.save("MKD").get();

            dataInitialized = true;
        }
    }

    @Test
    public void testDonationCausesPage() throws Exception {
        //unauthorized
        DonationCausesPage donationCausesPage = DonationCausesPage.to(this.driver);
        donationCausesPage.assertElemts(2,0,0,0,0);

        //logged in as user
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        LoginPage.login(this.driver, loginPage, regularUser.getUsername(), "pass");
        donationCausesPage = DonationCausesPage.to(this.driver);
        Thread.sleep(2000);
        donationCausesPage.assertElemts(2,0,0,0,0);

        //logged in as admin
        loginPage = LoginPage.openLoginPage(this.driver);
        LoginPage.login(this.driver, loginPage, adminUser.getUsername(), "pass");
        donationCausesPage = DonationCausesPage.to(this.driver);
        Thread.sleep(2000);
        donationCausesPage.assertElemts(2,2,2,1,1);

    }
}
