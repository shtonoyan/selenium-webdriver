package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailHomePage extends BasePage{
    public GmailHomePage(WebDriver driver) {
        super(driver);
    }
//    @FindBy(id="identifierId")
//    private WebElement loginField;
//
//    @FindBy(xpath = "//input[@type='password']")
//    private WebElement passwordField;
//
//    @FindBy(xpath = "//span[@class='RveJvd snByac']")
//    private WebElement nextBtn;



    public GmailHomePage open(){
        driver.get("http://gmail.com");
        return this;
    }

    public GmailAccountPage login(){

        WebElement loginField =new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("identifierId")));

        loginField.sendKeys("shtonoyan.test");

        WebElement nextBtn = new WebDriverWait(driver,10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='RveJvd snByac']")));
        nextBtn.click();
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));

        WebElement passwordField = new WebDriverWait(driver,10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='password']")));
        passwordField.sendKeys("april192020");
        nextBtn = new WebDriverWait(driver,10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='RveJvd snByac']")));
        nextBtn.click();

        GmailAccountPage gmailAccountPage = new GmailAccountPage(driver);
        return gmailAccountPage;
    }
}
