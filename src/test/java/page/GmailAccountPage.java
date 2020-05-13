package page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
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

    @FindBy(xpath = "//tr[contains(@class,'zA')]")
    private List<WebElement> mails;

    @FindBy(xpath = "//input[@aria-label=\"Search mail\"]")
    private WebElement searchField;

    @FindBy(xpath = "//button[@data-tooltip='Show search options']")
    private WebElement searchOptions;

    @FindBy(xpath = "//input[@class='ZH nr aQa']")
    private WebElement searchOptionFrom;

    @FindBy(xpath = "//input[@class='ZH nr aQd']")
    private WebElement searchOptionSubject;

    @FindBy(xpath = "//div[@aria-label='Search Mail']")
    private WebElement searchBtn;

    @FindBy(xpath = "//span[@id and text()='Reply']")
    private WebElement replyBtn;

    @FindBy(xpath = "//img[@data-name='Shushanik Tonoyan']")
    private WebElement repliedSign;

    @FindBy(xpath = "//div[text()='Inbox']/ancestor::td/preceding-sibling::td/child::span[@title='Not starred']")
    private List<WebElement> stars;

    @FindBy(xpath = "//*[@data-tooltip='Save & close']")
    private WebElement closeBtn;

    @FindBy(xpath = "//div[@class='dC']")
    private WebElement sendBtn;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement attachFile;

    @FindBy(xpath = "//span[@class='CJ']")
    private WebElement moreBtn;

    @FindBy(xpath = "//a[contains(@aria-label, 'Google Account') ]")
    private WebElement googleAccount;

    @FindBy(xpath = "//a[text()='Sign out']")
    private WebElement signOutBtn;

    protected GmailAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginSuccessfull() {
        logo = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(logo));
        return logo.isDisplayed();
    }

    public GmailAccountPage composeNewMail() {
        composeBtn.click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//div[@class='AD']")));
        return this;
    }

    public GmailAccountPage fillAddress(String recipient) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(address));

        address.sendKeys(recipient);
        return this;
    }

    public GmailAccountPage fillTitle(String title) {
        subject.sendKeys(title);
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//div[contains(text(),'" + title + "')]")));
        return this;
    }

    public GmailAccountPage writeMessage(String messageContent) {
        contentBox.sendKeys(messageContent);

        return this;
    }

    public void saveAsDraft() {
        closeBtn.click();
    }

    public GmailAccountPage openDrafts() {
        menuItems.get(4).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//span[@class='bog']")));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//span[@class='bog']")));
        return this;
    }

    public boolean isNewDraftSaved(String title) {
        for (WebElement element : drafItems) {
            if (element.getText().equals(title))
                break;
            return true;
        }
        return false;
    }

    public void openDraftMail(String title) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By
                                .xpath("//tr[.//*[contains(text(),'Draft')]]//td[@id]//span[contains(text(),'" + title + "')]")));

        JavascriptExecutor jsExec = (JavascriptExecutor) driver;

        WebElement draft = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .elementToBeClickable(By
                                .xpath("//tr[.//*[contains(text(),'Draft')]]//td[@id]//span[contains(text(),'" + title + "')]")));
        jsExec.executeScript("arguments[0].click();", draft);
    }

    public void sendMail() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(sendBtn));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(sendBtn));
        sendBtn.click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//span[text()='Message sent.']")));
    }

    public GmailAccountPage openSentMails() {
        menuItems.get(3).click();

        return new GmailAccountPage(this.driver);
    }

    public boolean isMailInSentMails(String title) {

        WebElement sentMail = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//tr[.//div[text()='To: ']]//td[@id]//span[contains(text(),'" + title + "')]")));

        return sentMail.isDisplayed();
    }

    public GmailAccountPage openSearchOptions() {
        searchOptions.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchBtn));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(searchBtn));
        return this;
    }

    public GmailAccountPage fillSearchOptionFrom(String address) {
        searchOptionFrom.sendKeys(address);
        return this;
    }

    public GmailAccountPage fillSearchOptionSubject(String subject) {
        searchOptionSubject.sendKeys(subject);
        return this;
    }

    public void search() {
        searchBtn.click();
    }

    public GmailAccountPage openMailFromAddress(String address) {
        WebElement searchResult = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//tr[.//span[@email='" + address + "']]//div[text()='Inbox']")));
        searchResult.click();
        return this;
    }

    public GmailAccountPage reply() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(replyBtn));
        replyBtn.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(sendBtn));
        return this;
    }

    public void starrMails() {
        stars.forEach(WebElement::click);
    }

    public GmailAccountPage openStarredMails() {
        menuItems.get(1).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//div[text()='Inbox']")));
        return this;
    }

    public void unstarrMailsFromSender(String address) {
        List<WebElement> starred = driver.findElements(By
                .xpath("//div[text()='Inbox']//ancestor::tr[contains(@class,'zA') and . //span[@email='" + address + "']]//span[@title='Starred']"));
        for (WebElement element : starred) {
            element.click();
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions
                            .invisibilityOf(element));
        }
    }

    public List<Boolean> areMailsFromSenderStarred(String sender) {
        List<WebElement> starredMails = driver.findElements(By.xpath("//tr[.//span[@email='" + sender + "']]//div[text()='Inbox']"));
        List<Boolean> areDisplayed = new ArrayList<>();

        starredMails.forEach(mail -> areDisplayed.add(mail.isDisplayed()));

        return areDisplayed;
    }

    public boolean areMailsFromSenderUnstarred(String address) {
        return driver.findElements(By
                .xpath("//div[text()='Inbox']//ancestor::tr[contains(@class,'zA') and . //span[@email='" + address + "']]//span[@title='Starred']"))
                .size() == 0;
    }

    public GmailAccountPage attachFile(String filePath) {
        attachFile.sendKeys(filePath);
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@aria-label='Remove attachment']")));

        return new GmailAccountPage(this.driver);
    }

    public GmailAccountPage boldTheMailContent() {

        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").sendKeys("b").keyUp(Keys.CONTROL).build().perform();
        return new GmailAccountPage(this.driver);
    }

    public void dropMailToTrashBin(String title) {
        openSearchOptions().fillSearchOptionSubject(title).search();
        WebElement mail = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//div[text()='Inbox']//ancestor::div[@class='xT']")));
        highlightElement(driver, mail);

        moreBtn.click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(menuItems.get(10)));
        new Actions(driver).dragAndDrop(mail, menuItems.get(10)).build().perform();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .invisibilityOfElementLocated(By.xpath("//span[contains(text(),'Conversation')]")));

    }

    public GmailAccountPage openTrashBit() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'aim')]")));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .elementToBeClickable(menuItems.get(10)));

        menuItems.get(10).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.xpath("//span[@class='bog']")));
        return this;
    }

    public boolean isMailInTrashBin(String title) {
        openSearchOptions().fillSearchOptionSubject(title).search();
        WebElement trashMail = new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//tr[.//div[text()='Trash']]//td[@id]//span[contains(text(),'" + title + "')]")));

        return trashMail.isDisplayed();
    }

    private void highlightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
        jsExecutor.executeScript("arguments[0].style.backgroundColor = 'yellow'", element);
    }

    public GmailAccountPage clickGoogleAccount() {
        googleAccount.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signOutBtn));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(signOutBtn));

        return this;
    }

    public GmailHomePage logout() {
        signOutBtn.click();
        return new GmailHomePage(this.driver);
    }
}
