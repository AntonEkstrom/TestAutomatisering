package StepDefPack;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;
public class MyStepDefs {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I open the browser")
    public void iOpenTheBrowse() {

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.edge.driver", "D:/Selenium/msedgedriver.exe");

        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().window().maximize();

    }

    @Given("I enter an {string} address")
    public void iEnterAnAddress(String email){

        if (email.equalsIgnoreCase("666")) {
            sendKeysToElement(By.id("email"), email);
        }
        if (email.equalsIgnoreCase("Antontavla@tavlor.se")) {
            sendKeysToElement(By.id("email"), "Antontavla@tavlor.se");

        } else {
            Random randomEmail = new Random();
            int randomCombination = randomEmail.nextInt(1000);
            sendKeysToElement(By.id("email"), randomCombination + email);
        }

    }

    @And("I type an {string} and a {string}")
    public void iTypeAnAndA(String userName, String passWord) {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));

        WebElement usernameField = driver.findElement(By.id("new_username"));
        usernameField.click();
        usernameField.clear();

        if (userName.equals("Lars")) {
            String randomUsername = generateRandomUsername(111) + userName;
            sendKeysToElement(By.id("new_username"), randomUsername);
            clickElement(By.name("password"));
            sendKeysToElement(By.id("new_password"), passWord);
        } else if (userName.equals("sixsixsix")) {
            sendKeysToElement(By.id("new_username"), "sixsixsix");
            sendKeysToElement(By.id("new_password"), passWord);
        } else {
            Random randomUserName = new Random();
            int randomCombination = randomUserName.nextInt(1000);
            sendKeysToElement(By.id("new_username"), randomCombination + userName);
            sendKeysToElement(By.id("new_password"), passWord);
        }

    }

    @Then("I click on the sign-up button and the user is {string}")
    public void iClickOnTheSignUpButtonAndTheUserIs(String userCreated){

        driver.findElement(By.id("create-account-enabled")).click();

        if (userCreated.equalsIgnoreCase("success")) {
            verifySuccessMessage();
        } else if (userCreated.equalsIgnoreCase("fail")) {
            verifyErrorMessage("Enter a value less than 100 characters long");
        } else if (userCreated.equalsIgnoreCase("failed")) {
            verifyErrorMessage("Great minds think alike - someone already has this username. If it's you, log in.");
        } else if (userCreated.equalsIgnoreCase("failing")) {
            verifyErrorMessage("An email address must contain a single @.");
        }

    }

    private void verifySuccessMessage() {

        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-success > div > div.content.line.section > section > div > h1")));
        String expected = "Check your email";
        String actual = successMessage.getText();
        assertEquals(expected, actual);

    }

    private void verifyErrorMessage(String expectedErrorMessage) {

        WebElement errorMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error")));
        String actualErrorMessage = errorMessageElement.getAttribute("value");
        assertEquals(expectedErrorMessage, actualErrorMessage);

    }

    @After
    public void terminate() {

        driver.close();
        driver.quit();

    }

    public String generateRandomUsername(int length) {

        StringBuilder username = new StringBuilder();
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            username.append(randomChar);
        }

        return username.toString();
    }

    private void sendKeysToElement(By by, String text) {

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.clear();
        element.sendKeys(text);

    }

    private void clickElement(By by) {

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.click();

    }

}