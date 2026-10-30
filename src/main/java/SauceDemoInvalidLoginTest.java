
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoInvalidLoginTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name"))
                  .sendKeys("invalid_user");

            driver.findElement(By.id("password"))
                  .sendKeys("wrong_password");

            driver.findElement(By.id("login-button"))
                  .click();

            WebDriverWait wait = new WebDriverWait(
                    driver, Duration.ofSeconds(10));

            String errorMessage = wait.until(d ->
                    d.findElement(By.cssSelector("[data-test='error']"))
                     .getText());

            if (errorMessage.contains(
                    "Username and password do not match")) {

                System.out.println("INVALID LOGIN TEST PASSED");

            } else {
                System.out.println("INVALID LOGIN TEST FAILED");
            }

        } catch (Exception e) {
            System.out.println("INVALID LOGIN TEST FAILED");
            e.printStackTrace();

        } finally {
            driver.quit();
        }
    }
}
