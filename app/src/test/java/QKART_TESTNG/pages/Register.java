package QKART_TESTNG.pages;

import java.sql.Timestamp;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Register {
    WebDriver driver;

    // Registration page url
    String url = "https://crio-qkart-frontend-qa.vercel.app/register";

    // Creating String variable for storing last generated username
    public String lastGeneratedUsername = "";

    // Constructor of Register class
    public Register(WebDriver driver) {
        this.driver = driver;
    }

    // Opening the registration page
    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }

    public Boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic)
            throws InterruptedException {
        // Locating the Username Text Box
        WebElement username_txt_box = this.driver.findElement(By.id("username"));

        // Getting time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_data_username;
        if (makeUsernameDynamic)
            // Concatenate the timestamp to string to form unique timestamp
            test_data_username = Username + "_" + String.valueOf(timestamp.getTime());
        else
            test_data_username = Username;

        // Type the generated username in the username field
        username_txt_box.sendKeys(test_data_username);

        // Find the password Text Box
        WebElement password_txt_box = this.driver.findElement(By.id("password"));
        String test_data_password = Password;

        // Enter the Password value
        password_txt_box.sendKeys(test_data_password);

        WebElement confirm_password_txt_box = this.driver.findElement(By.id("confirmPassword"));

        // Enter the Confirm Password Value
        confirm_password_txt_box.sendKeys(test_data_password);

        // Find the register now button
        WebElement register_now_Btn = this.driver.findElement(By.className("button"));

        // Click the register now button
        register_now_Btn.click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(
                    ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/login"));
        } catch (TimeoutException e) {
            return false;
        }

        this.lastGeneratedUsername = test_data_username;

        return this.driver.getCurrentUrl().endsWith("/login");
    }
}