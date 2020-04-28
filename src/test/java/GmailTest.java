import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.GmailAccountPage;
import page.GmailHomePage;

public class GmailTest {
    String login = "shtonoyan.test";
    String password = "april192020";
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void browserSetup() {
        driver = new ChromeDriver();
    }

    @Test
    public void createDraftSendIt() {
        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.login(login, password);

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

        Assert.assertTrue(gmailAccountPage.isMailInSentMails(title));
    }

    @Test
    public void replyTheMail() {
        String address = "sh.tonoyan@ysu.am";
        String subject = "My mail";
        String messageContent = "Text";
        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.login(login, password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        gmailAccountPage.openSearchOptions().fillSearchOptionFrom(address).fillSearchOptionSubject(subject).search();
        gmailAccountPage.openMailFromAddress(address).reply().writeMessage(messageContent).sendMail();

        Assert.assertTrue(gmailAccountPage.isMailInSentMails(subject));

        gmailAccountPage.clickGoogleAccount().logout().isLogoutSuccessfull();
    }

    @Test
    public void addMailsToStarred() {
        String address = "sh.tonoyan@gmail.com";

        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.login(login, password);

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        gmailAccountPage.openSearchOptions().fillSearchOptionFrom(address).search();
        gmailAccountPage.starrMails();
        gmailAccountPage.openStarredMails()
                .areMailsFromSenderStarred(address).forEach(Assert::assertTrue);

        gmailAccountPage.unstarrMailsFromSender(address);
        Assert.assertTrue(gmailAccountPage.areMailsFromSenderUnstarred(address));
    }

    @AfterClass
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }
}
