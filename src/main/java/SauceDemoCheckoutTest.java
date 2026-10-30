
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class SauceDemoCheckoutTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        try {
            // STEP 1: Open Sauce Demo
            driver.get("https://www.saucedemo.com/");

            // STEP 2: Login
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("user-name")))
                    .sendKeys("standard_user");

            driver.findElement(By.id("password"))
                    .sendKeys("secret_sauce");

            driver.findElement(By.id("login-button"))
                    .click();

            wait.until(ExpectedConditions.urlContains("inventory.html"));

            System.out.println("Login successful");

            // STEP 3: Add backpack to cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("add-to-cart-sauce-labs-backpack")))
                    .click();

            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.className("shopping_cart_badge"), "1"));

            System.out.println("Product added to cart");

            // STEP 4: Open cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.className("shopping_cart_link")))
                    .click();

            wait.until(ExpectedConditions.urlContains("cart.html"));

            // STEP 5: Begin checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("checkout")))
                    .click();

            wait.until(ExpectedConditions.urlContains(
                    "checkout-step-one.html"));

            // STEP 6: Enter customer information
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("first-name")))
                    .sendKeys("Test");

            driver.findElement(By.id("last-name"))
                    .sendKeys("User");

            driver.findElement(By.id("postal-code"))
                    .sendKeys("8001");

            // STEP 7: Continue checkout
            driver.findElement(By.id("continue"))
                    .click();

            wait.until(ExpectedConditions.urlContains(
                    "checkout-step-two.html"));

            // STEP 8: Finish checkout
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("finish")))
                    .click();

            // STEP 9: Verify confirmation
            wait.until(ExpectedConditions.urlContains(
                    "checkout-complete.html"));

            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.className("complete-header"),
                    "Thank you for your order!"));

            System.out.println("CHECKOUT TEST PASSED");

        } catch (Exception e) {
            System.out.println("CHECKOUT TEST FAILED");
            e.printStackTrace();
            throw new RuntimeException("Checkout test failed", e);

        } finally {
            driver.quit();
        }
    }
}
