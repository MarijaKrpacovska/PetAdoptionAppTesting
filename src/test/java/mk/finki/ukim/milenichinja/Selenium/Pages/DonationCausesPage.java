package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class DonationCausesPage extends BasePage   {

    @FindBy(css = ".edenDC")
    private List<WebElement> causes;

    @FindBy(css = ".delete_button")
    private List<WebElement> deleteButtons;

    @FindBy(css = ".edit")
    private List<WebElement> editButtons;

    @FindBy(css = ".addDC")
    private List<WebElement> addButton;

    @FindBy(css = ".allDonatons")
    private List<WebElement> allDonations;


    public DonationCausesPage(WebDriver driver) {
        super(driver);
    }

    public static DonationPage to(WebDriver driver) {
        get(driver, "/causes");
        return PageFactory.initElements(driver, DonationPage.class);
    }

    public void assertElemts(int causesNumber, int deleteButtons, int editButtons, int addButtons, int viewDonationsButton) {
        Assert.assertEquals("causes number does not match", causesNumber, this.getCauses().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("viewDonationsButton do not match", viewDonationsButton, this.getAllDonations().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddButton().size());
    }

}
