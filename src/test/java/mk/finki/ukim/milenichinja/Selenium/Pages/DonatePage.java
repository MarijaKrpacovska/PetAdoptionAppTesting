//package mk.finki.ukim.milenichinja.Selenium.Pages;
//
//import lombok.Getter;
//import org.junit.Assert;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//
//import java.util.List;
//
//@Getter
//public class DonatePage extends BasePage {
//
//    @FindBy(css = "#sum")
//    private List<WebElement> sumInput;
//
//    @FindBy(css = "#cardNumber")
//    private List<WebElement> cardNumberInput;
//
//    @FindBy(css = "#valuteSelect")
//    private List<WebElement> valuteInput;
//
//    @FindBy(css = "#paymentMethod")
//    private List<WebElement> paymentMethodInput;
//
//    @FindBy(css = "#dcId")
//    private List<WebElement> dcIdInput;
//
//    @FindBy(css = ".donate_button")
//    private List<WebElement> donateButton;
//
//    @FindBy(css = ".wwudf")
//    private WebElement whatWeUseDonationsForLink;
//
//    public DonatePage(WebDriver driver) {
//        super(driver);
//    }
//
//    public static DonatePage to(WebDriver driver) {
//        get(driver, "/donate");
//        return PageFactory.initElements(driver, DonatePage.class);
//    }
//
//    public static PetsPage donate(WebDriver driver, DonatePage donatePage,String sum, String cardNum) {
//        donatePage.sumInput.get(0).sendKeys(sum);
//        donatePage.cardNumberInput.get(0).sendKeys(cardNum);
//        donatePage.donateButton.get(0).click();
//        System.out.println(driver.getCurrentUrl());
//        return PageFactory.initElements(driver, PetsPage.class);
//    }
//
////    public DonatePage fillInForm(WebDriver driver, DonatePage donatePage) {
////        donatePage.donateButton.get(0).click();
////        System.out.println(driver.getCurrentUrl());
////        return PageFactory.initElements(driver, DonatePage.class);
////    }
////
////    public DonatePage clickOnDonateButton(WebDriver driver, DonatePage donatePage) {
////        donatePage.donateButton.get(0).click();
////        System.out.println(driver.getCurrentUrl());
////        return PageFactory.initElements(driver, DonatePage.class);
////    }
//
//    public AddOrEditCenterPage clickOnWhatWeUseDonationsForLink(WebDriver driver, DonatePage donatePage) {
//        donatePage.whatWeUseDonationsForLink.click();
//        System.out.println(driver.getCurrentUrl());
//        return PageFactory.initElements(driver, AddOrEditCenterPage.class);
//    }
//
//    public void assertElemts() {
//        Assert.assertEquals("sum input missing", 1, this.sumInput.size());
//        Assert.assertEquals("card number input missing", 1, this.cardNumberInput.size());
//        Assert.assertEquals("valute input missing", 1, this.valuteInput.size());
//        Assert.assertEquals("payment method input missing", 1, this.paymentMethodInput.size());
//        Assert.assertEquals("DC id input missing", 1, this.dcIdInput.size());
//        Assert.assertEquals("button missing", 1, this.donateButton.size());
//    }
//}
