package org.apicodes;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSetup {
    public static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup(); // For Chrome
        return new ChromeDriver();
    }
}
