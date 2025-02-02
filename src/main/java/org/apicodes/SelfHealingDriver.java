package org.apicodes;

import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelfHealingDriver {
    private WebDriver driver;
    private static OllamaClient ollama;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public static OllamaClient getOllama() {
        return ollama;
    }

    public void setOllama(OllamaClient ollama) {
        ollama = ollama;
    }

    private final WebDriverWait wait;

    public SelfHealingDriver(WebDriver driver) {
        this.driver = driver;
        ollama = new OllamaClient();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    // Self-healing findElement method
    public WebElement findElementWithHealing(String originalLocator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(originalLocator)));
        } catch (Exception e) {
            return attemptSelfHealing(By.xpath(originalLocator), e);
        }
    }

    private WebElement attemptSelfHealing(By originalLocator, Exception e) {
        try {

            String prompt = String.format(
                    "As a QA automation expert, suggest 1 alternative Selenium xpath locators for this element. " +
                            "Original locator '%s' failed with error: '%s'. HTML snippet: '''%s''' " +
                            "Respond ONLY with valid 1 xpath locator format: '//tag[@attribute = 'value']'",
                    originalLocator, e, driver.getPageSource().substring(0, Math.min(driver.getPageSource().length(), 2000))
            );

            Thread.sleep(2000);
            String newSelector = ollama.generateText("qwen2.5-coder:3b", prompt);
            String newLocator= extractXPathLocator(newSelector);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(newLocator)));
        } catch (Exception ex) {
            throw new NoSuchElementException("Self-healing failed: " + ex.getMessage());
        }
    }


    public static String extractXPathLocator(String aiResponse) {
        // Regex to match XPath locators starting with "//"
        String regex = "//[^\\s\\]]+\\[[^\\]]+\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aiResponse);

        // Find the first match
        if (((Matcher) matcher).find()) {
            return matcher.group(0);
        } else {
            throw new IllegalArgumentException("No valid XPath locator found in the response.");
        }
    }


    public void closeAll() {
        this.driver.quit();
    }

}
