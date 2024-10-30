package com.test.automation.actions;

import io.qameta.allure.Step;
import jdk.dynalink.beans.StaticClass;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class WebActions {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public WebActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    @Step("Click element: {0}")
    public void click(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            log.info("Clicked element: {}", locator);
            
        } catch (Exception e) {
            log.error("Failed to click element: {}", locator, e);
            throw e;
        }
    }

    // Global method to construct the XPath based on product name
    private String getStringFormatted(String productName) {
        return String.format("//h2[contains(text(),'%s')]/ancestor::a/following-sibling::a[contains(@class, 'add_to_cart_button')]", productName);
    }

    // Click method that accepts product name and locator
    @Step("Click 'Add to Cart' button for product: {0} and locator: {1}")
    public void click(String productName, By locator) {
        try {
            // Generate the actual XPath using the product name in the template
           String replacedXpath= locator.toString().replace("By.xpath: ", "");
            String formattedXPath = String.format(replacedXpath.toString(), productName);
            //log.info("formattedXPath :",formattedXPath);
            System.out.println("formattedXPath = " + formattedXPath);

            // Create the locator from the formatted XPath
            By locatorFormatted = By.xpath(formattedXPath);

            // Wait until the element is clickable and perform the click
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locatorFormatted));
            element.click();

            log.info("Clicked 'Add to Cart' button for product: {}", productName);
        } catch (Exception e) {
            log.error("Failed to click 'Add to Cart' button for product: {}", productName, e);
            throw e;
        }
    }



    @Step("Enter text: {1} into element: {0}")
    public void enterText(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            //log.info("Entered text: {} into element: {}", text, locator);
        } catch (Exception e) {
            log.error("Failed to enter text: {} into element: {}", text, locator, e);
            throw e;
        }
    }

    @Step("Select option by text: {1} from dropdown: {0}")
    public void selectDropdownByText(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select select = new Select(element);
            select.selectByVisibleText(text);
            log.info("Selected option: {} from dropdown: {}", text, locator);
        } catch (Exception e) {
            log.error("Failed to select option: {} from dropdown: {}", text, locator, e);
            throw e;
        }
    }

    @Step("Select option from auto-suggest dropdown: {0} with text: {1}")
    public void selectAutoSuggestDropdown(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.sendKeys(text);
            By suggestion = By.xpath(String.format("//div[contains(text(),'%s')]", text));
            wait.until(ExpectedConditions.elementToBeClickable(suggestion)).click();
            log.info("Selected auto-suggest option: {} from dropdown: {}", text, locator);
        } catch (Exception e) {
            log.error("Failed to select auto-suggest option: {} from dropdown: {}", text, locator, e);
            throw e;
        }
    }

    @Step("Mouse hover over element: {0}")
    public void mouseHover(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            actions.moveToElement(element).perform();
            log.info("Mouse hovered over element: {}", locator);
        } catch (Exception e) {
            log.error("Failed to mouse hover over element: {}", locator, e);
            throw e;
        }
    }

    /**
     * Scrolls element into view and ensures it's visible
     * @param element WebElement to scroll to
     * @param timeoutSeconds Maximum time to wait for element to be visible
     * @return true if successful, false if operation fails
     */
    @Step("scroll To Element: {0}")
    public boolean scrollToElement(WebElement element, int timeoutSeconds) {
        try {
            // Create wait object
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

            // First scroll using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

            // Small pause to allow smooth scroll to complete
            Thread.sleep(500);

            // Wait for element to be visible
            wait.until(ExpectedConditions.visibilityOf(element));

            return true;
        } catch (Exception e) {
            System.err.println("Failed to scroll to element: " + e.getMessage());
            return false;
        }
    }

    /**
     * Overloaded method with default timeout of 10 seconds
     *
     */
    @Step("scroll To Element only : {0}")
    public boolean scrollToElement(WebElement element) {
        return scrollToElement(element, 10);
    }

    /**
     * Alternative scroll method using Actions class
     * Useful when JavaScript scroll doesn't work as expected
     */
    @Step("scroll To Element Using Actions: {0}")
    public boolean scrollToElementUsingActions(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            return true;
        } catch (Exception e) {
            System.err.println("Failed to scroll using Actions: " + e.getMessage());
            return false;
        }
    }

    /**
     * Scrolls to an element, waits for it to be visible and clickable, then enters text
     * @param element WebElement to scroll to and enter text
     * @param textToEnter Text to enter in the element
     * @param timeoutSeconds Maximum time to wait for element to be ready
     * @return true if successful, false if operation fails
     */

    @Step("scroll To Element And Enter Text: {0}")
    public boolean scrollAndEnterText(WebElement element, String textToEnter, int timeoutSeconds) {
        try {
            // First scroll to element
            if (!scrollToElement(element, timeoutSeconds)) {
                return false;
            }

            // Create wait object
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

            // Wait for element to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // Clear existing text and enter new text
            element.clear();
            element.sendKeys(textToEnter);

            return true;
        } catch (Exception e) {
            System.err.println("Failed to scroll and enter text: " + e.getMessage());
            return false;
        }
    }

    /**
     * Overloaded method with default timeout of 10 seconds
     */

    @Step("scroll To Element And Enter Text with Timeout: {0}")
    public boolean scrollAndEnterText(WebElement element, String textToEnter) {
        return scrollAndEnterText(element, textToEnter, 10);
    }


    /**
     * Gets text from a WebElement with wait and error handling
     */
    @Step("Get text from element: {element}")
    public String getElementText(WebElement element, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));

            String text = element.getText();
            // If getText() returns empty, try getting value attribute
            if (text == null || text.trim().isEmpty()) {
                text = element.getAttribute("value");
            }

            String result = text != null ? text.trim() : "";
            log.info("Retrieved text: " + result);
            return result;

        } catch (Exception e) {
            log.error("Failed to get element text: " + e.getMessage());
            return "";
        }
    }

    /**
     * Overloaded method with default 10 second timeout
     */
    @Step("Get text from element with default timeout: {element}")
    public  String getElementText(WebElement element) {
        return getElementText(element, 10);
    }

    /**
     * Gets attribute value from a WebElement with wait and error handling
     */
    @Step("Get attribute '{attributeName}' from element: {element}")
    public String getElementAttribute(WebElement element, String attributeName, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(".//*[@" + attributeName + "]")));

            String attributeValue = element.getAttribute(attributeName);
            String result = attributeValue != null ? attributeValue.trim() : "";
            log.info("Retrieved attribute '" + attributeName + "': " + result);
            return result;

        } catch (Exception e) {
            log.error("Failed to get attribute '" + attributeName + "': " + e.getMessage());
            return "";
        }
    }

    /**
     * Overloaded method with default 10 second timeout
     */
    @Step("Get attribute '{attributeName}' from element with default timeout: {element}")
    public String getElementAttribute(WebElement element, String attributeName) {
        return getElementAttribute(element, attributeName, 10);
    }

    /**
     * Gets multiple attributes from a WebElement
     */
    @Step("Get multiple attributes {attributeNames} from element: {element}")
    public Map<String, String> getMultipleAttributes(WebElement element, String... attributeNames) {
        Map<String, String> attributes = new HashMap<>();
        for (String attributeName : attributeNames) {
            attributes.put(attributeName, getElementAttribute(element, attributeName));
        }
        log.info("Retrieved multiple attributes: " + attributes);
        return attributes;
    }

    /**
     * Checks if element contains specific text
     */
    @Step("Check if element contains text '{expectedText}': {element}")
    public boolean elementContainsText(WebElement element, String expectedText) {
        String actualText = getElementText(element);
        boolean contains = actualText.contains(expectedText);
        log.info("Element contains text '" + expectedText + "': " + contains);
        return contains;
    }

    /**
     * Gets inner HTML of an element
     */
    @Step("Get inner HTML from element: {element}")
    public String getInnerHtml(WebElement element) {
        String html = getElementAttribute(element, "innerHTML");
        log.info("Retrieved inner HTML: " + html);
        return html;
    }

    /**
     * Waits for text to be present in element
     */
    @Step("Wait for text '{expectedText}' to be present in element: {element}")
    public boolean waitForText(WebElement element, String expectedText, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            boolean result = wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
            log.info("Text '" + expectedText + "' present: " + result);
            return result;
        } catch (Exception e) {
            log.error("Text '" + expectedText + "' not present after " + timeoutSeconds + " seconds");
            return false;
        }
    }

    /**
     * Waits for attribute to have specific value
     */
    @Step("Wait for attribute '{attributeName}' to have value '{expectedValue}' in element: {element}")
    public boolean waitForAttributeValue(WebElement element, String attributeName,
                                         String expectedValue, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            boolean result = wait.until(ExpectedConditions.attributeToBe(element, attributeName, expectedValue));
            log.info("Attribute '" + attributeName + "' has expected value: " + result);
            return result;
        } catch (Exception e) {
            log.error("Attribute '" + attributeName + "' did not have expected value after "
                    + timeoutSeconds + " seconds");
            return false;
        }
    }
}
