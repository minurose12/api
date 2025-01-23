package ebay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ebayAddToCart 
{
    public static void main(String[] args) throws InterruptedException 
    {
        
        WebDriver driver = new ChromeDriver();

        try {
            // 1. Open browser and maximize window
            driver.manage().window().maximize();
            
            //implicit and explicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // 2. Navigate to ebay.com
            driver.get("https://www.ebay.com");

            // 3. Search for 'book'
            WebElement searchBox = driver.findElement(By.id("gh-ac"));
            searchBox.sendKeys("book");
            WebElement searchButton = driver.findElement(By.id("gh-search-btn"));
            searchButton.click();

            // 4. Click on the first book in the list
            WebElement firstBook = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class,'srp-results srp-list clearfix')]/li[1]//a[contains(@class,'s-item__link')]")));
            firstBook.click();

            // 5. Page opens in new tab (Switch to new tab)
            String originalWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }

            // 6. In the item listing page, click on 'Add to cart'
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Add to cart')]")));
            addToCartButton.click();

            // 7. Verify the cart has been updated
            Thread.sleep(3000); // Wait for cart update
            WebElement cartCountElement = driver.findElement(By.xpath("//*[contains(@class,'gh-cart__icon')]//span"));
            String cartCount = cartCountElement.getText();
            if (cartCount.equals("1")) {
                System.out.println("Test Passed: Cart has been updated successfully.");
            } else {
                System.out.println("Test Failed: Cart update verification failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}