import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.GmailAccountPage;
import page.GmailHomePage;

import java.net.MalformedURLException;
import java.net.URL;

public class GmailTest {
    String login = "shtonoyan.test";
    String password = "april192020";
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void browserSetup() {
//        driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "D:\\webdriver\\chromedriver.exe");
        DesiredCapabilities capability = DesiredCapabilities.chrome();

        capability.setPlatform(Platform.WINDOWS);
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDraftSendIt() {
        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage
                .fillLoginField(login).fillPasswordField(password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        String title = "Test mail";
        String messageContent = "Test mail content";
        String recipient = "sh.tonoyan@gmail.com";

        gmailAccountPage
                .composeNewMail()
                .fillAddress(recipient)
                .fillTitle(title)
                .writeMessage(messageContent)
                .saveAsDraft();

        Assert.assertTrue(gmailAccountPage.openDrafts().isNewDraftSaved(title));

        gmailAccountPage.openDraftMail("Test mail");

        gmailAccountPage.sendMail();

        Assert.assertTrue(gmailAccountPage.openSentMails().isMailInSentMails(title));
    }

    @Test
    public void replyTheMail() {
        String address = "sh.tonoyan@ysu.am";
        String subject = "My mail";
        String messageContent = "Text";
        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.fillLoginField(login).fillPasswordField(password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        gmailAccountPage.openSearchOptions().fillSearchOptionFrom(address).fillSearchOptionSubject(subject).search();
        gmailAccountPage.openMailFromAddress(address).reply().writeMessage(messageContent).sendMail();

        Assert.assertTrue(gmailAccountPage.openSentMails().isMailInSentMails(subject));

        Assert.assertTrue(gmailAccountPage.clickGoogleAccount().logout().isLogoutSuccessfull());
    }

    @Test
    public void addMailsToStarred() {
        String address = "sh.tonoyan@gmail.com";

        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.fillLoginField(login).fillPasswordField(password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        gmailAccountPage.openSearchOptions().fillSearchOptionFrom(address).search();
        gmailAccountPage.starrMails();
        gmailAccountPage.openStarredMails()
                .areMailsFromSenderStarred(address).forEach(Assert::assertTrue);
        gmailAccountPage.unstarrMailsFromSender(address);
        Assert.assertTrue(gmailAccountPage.areMailsFromSenderUnstarred(address));

        Assert.assertTrue(gmailAccountPage.clickGoogleAccount().logout().isLogoutSuccessfull());
    }

    @Test
    public void sendMailWithAttachment() {
        String address = "sh.tonoyan@gmail.com";
        String title = "Hi";
        String recipient = "sh.tonoyan@ysu.am";
        String content = "aaa";
        String path = "C:\\Users\\Shushanik_Tonoyan\\Desktop\\dog.jpg";

        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.fillLoginField(login).fillPasswordField(password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        gmailAccountPage
                .composeNewMail()
                .fillAddress(recipient)
                .fillTitle(title)
                .writeMessage(content)
                .boldTheMailContent()
                .attachFile(path)
                .sendMail();

        Assert.assertTrue(gmailAccountPage.openSentMails().isMailInSentMails(title));

        Assert.assertTrue(gmailAccountPage.clickGoogleAccount().logout().isLogoutSuccessfull());
    }

    @Test
    public void dropMailToTheTrashBin() {
        String title = "Second mail";

        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.fillLoginField(login).fillPasswordField(password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        gmailAccountPage.dropMailToTrashBin(title);
        Assert.assertTrue(gmailAccountPage.openTrashBit().isMailInTrashBin(title));
    }

    @AfterClass
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }
}
