
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoLoginTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        try {
            // Open the Sauce Demo website
            driver.get("https://www.saucedemo.com/");

            // Enter login credentials
            driver.findElement(By.id("user-name"))
                  .sendKeys("standard_user");

            driver.findElement(By.id("password"))
                  .sendKeys("secret_sauce");

            // Click the login button
            driver.findElement(By.id("login-button")).click();

            // Wait until the inventory page loads
            WebDriverWait wait = new WebDriverWait(
                    driver, Duration.ofSeconds(10));

            wait.until(d -> d.getCurrentUrl()
                    .contains("inventory.html"));

            // Verify the login result
            if (driver.getCurrentUrl().contains("inventory.html")) {
                System.out.println("LOGIN TEST PASSED");
            } else {
                System.out.println("LOGIN TEST FAILED");
            }

        } catch (Exception e) {
            System.out.println("LOGIN TEST FAILED");
            e.printStackTrace();

        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
