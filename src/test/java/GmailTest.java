import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.GmailAccountPage;
import page.GmailHomePage;

public class GmailTest {
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void browserSetup() {
        driver = new ChromeDriver();
    }

    @Test
    public void testSuccessfullLogin(){
        GmailHomePage gmailHomePage = new GmailHomePage(driver);
        gmailHomePage.open();
        GmailAccountPage gmailAccountPage = gmailHomePage.login();

        Assert.assertTrue(gmailAccountPage.isLoginSuccessfull(), "Not successfull login");

        String title = "Test mail";
        String messageContent = "Test mail content";
        String recipient = "sh.tonoyan@gmail.com";

        gmailAccountPage.createNewDraft(recipient, title, messageContent);

        Assert.assertTrue(gmailAccountPage.isNewDraftSaved());

        gmailAccountPage.openDraftMail("Test mail");

        gmailAccountPage.sendMail();

        Assert.assertTrue(gmailAccountPage.isMailInSentMails(title));
    }
    @AfterClass
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }

}
