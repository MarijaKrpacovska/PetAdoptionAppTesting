package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class DonationPage extends BasePage  {
    @FindBy(css = "#sum")
    private List<WebElement> sumInput;

    @FindBy(css = "#cardNumber")
    private List<WebElement> cardNumberInput;

    @FindBy(css = "#valuteSelect")
    private List<WebElement> valuteInput;

    @FindBy(css = "#paymentMethod")
    private List<WebElement> paymentMethodInput;

    @FindBy(css = "#dcId")
    private List<WebElement> dcIdInput;

    @FindBy(css = ".donate_button")
    private List<WebElement> donateButton;

    @FindBy(css = ".wwudf")
    private WebElement whatWeUseDonationsForLink;

    public DonationPage(WebDriver driver) {
        super(driver);
    }

    public static DonationPage to(WebDriver driver) {
        get(driver, "/donate");
        return PageFactory.initElements(driver, DonationPage.class);
    }

    public static PetsPage donate(WebDriver driver, DonationPage donatePage,String sum, String cardNum) {
        donatePage.sumInput.get(0).sendKeys(sum);
        donatePage.cardNumberInput.get(0).sendKeys(cardNum);
        donatePage.donateButton.get(0).click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, PetsPage.class);
    }

    public static DonationCausesPage clickOnWhatWeUseDonationsForLink(WebDriver driver, DonationPage donatePage) {
        donatePage.whatWeUseDonationsForLink.click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, DonationCausesPage.class);
    }

    public void assertElemts(int sum, int cn, int valute, int pm, int dcId, int button) {
        Assert.assertEquals("sum input missing", sum, this.sumInput.size());
        Assert.assertEquals("card number input missing", cn, this.cardNumberInput.size());
        Assert.assertEquals("valute input missing", valute, this.valuteInput.size());
        Assert.assertEquals("payment method input missing", pm, this.paymentMethodInput.size());
        Assert.assertEquals("DC id input missing", dcId, this.dcIdInput.size());
        Assert.assertEquals("button missing", button, this.donateButton.size());
    }

}
