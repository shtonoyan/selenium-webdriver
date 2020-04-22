package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GmailAccountPage extends BasePage {
    @FindBy(xpath = " //img[@class='gb_ua']")
    private WebElement logo;

    @FindBy(xpath = "//div[text()='Compose']")
    private WebElement composeBtn;

    @FindBy(xpath = "//*[@name='to']")
    private WebElement address;

    @FindBy(xpath = "//input[@name='subjectbox']")
    private WebElement subject;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    private WebElement contentBox;

    @FindBy(xpath = "//div[contains(@class,'aim')]")
    private List<WebElement> menuItems;

    @FindBy(xpath = "//span[@class='bog']")
    private List<WebElement> drafItems;

    @FindBy(xpath = "//*[@data-tooltip='Save & close']")
    private WebElement closeBtn;

    @FindBy(xpath = "//div[@class='dC']")
    private WebElement sendBtn;

    protected GmailAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginSuccessfull() {
        logo = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(logo));
        return logo.isDisplayed();
    }

    public void createNewDraft(String recipient, String title, String messageContent) {
        composeBtn.click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//div[@class='AD']")));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(address));

        address.sendKeys(recipient);

        subject.sendKeys(title);

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(contentBox));

        contentBox.sendKeys(messageContent);

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//div[contains(text(),'Test mail')]")));

        closeBtn.click();
    }

    public boolean isNewDraftSaved() {
        menuItems.get(4).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//span[@class='bog']")));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//span[@class='bog']")));
        for (WebElement element : drafItems) {
            if (element.getText().equals("Test mail"))
                break;
            return true;
        }
        return false;
    }

    public void openDraftMail(String title) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//tr[.//*[contains(text(),'Draft')]]//td[@id]//span[contains(text(),'" + title + "')]")));
        WebElement draft = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//tr[.//*[contains(text(),'Draft')]]//td[@id]//span[contains(text(),'" + title + "')]")));
        draft.click();
    }

    public void sendMail() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(sendBtn));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(sendBtn));
        sendBtn.click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//span[text()='Message sent.']")));
    }

    public boolean isMailInSentMails(String title) {
        menuItems.get(3).click();

        WebElement sentMail = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//tr[.//div[text()='To: ']]//td[@id]//span[text()='" + title + "']")));

        return sentMail.isDisplayed();
    }
}
