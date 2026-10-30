
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

public class SauceDemoCartTest {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        try {
            // STEP 1: Open Sauce Demo
            driver.get("https://www.saucedemo.com/");

            // STEP 2: Log in
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("user-name")))
                    .sendKeys("standard_user");

            driver.findElement(By.id("password"))
                    .sendKeys("secret_sauce");

            driver.findElement(By.id("login-button"))
                    .click();

            // STEP 3: Wait for inventory page
            wait.until(ExpectedConditions.urlContains("inventory.html"));

            System.out.println("Login successful");

            // STEP 4: Add backpack to cart
            By addButton = By.id(
                    "add-to-cart-sauce-labs-backpack");

            wait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(
                            addButton))
                    .click();

            // Confirm the cart badge shows one item
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.className("shopping_cart_badge"), "1"));

            System.out.println("Backpack added to cart");

            // STEP 5: Open shopping cart
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.className("shopping_cart_link")))
                    .click();

            // Wait for cart page
            wait.until(ExpectedConditions.urlContains("cart.html"));

            // STEP 6: Verify backpack is in cart
            By backpack = By.cssSelector(
                    ".cart_item .inventory_item_name");

            boolean productFound = wait
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> d.findElements(backpack)
                            .stream()
                            .anyMatch(element ->
                                    element.getText().equals(
                                            "Sauce Labs Backpack")));

            if (productFound) {
                System.out.println("CART TEST PASSED");
            } else {
                System.out.println("CART TEST FAILED");
                System.exit(1);
            }

        } catch (Exception e) {
            System.out.println("CART TEST FAILED");
            e.printStackTrace();
            System.exit(1);

        } finally {
            driver.quit();
        }
    }
}
