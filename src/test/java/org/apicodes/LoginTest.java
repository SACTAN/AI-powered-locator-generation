package org.apicodes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    static SelfHealingDriver healingDriver;
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
         healingDriver = new SelfHealingDriver(driver);
          String  dir = System.getProperty("user.dir");
        System.out.println("file path "+ dir +  "/src/sample.html");
        driver.get(dir + "/src/sample.html");

        WebElement emailField = healingDriver.findElementWithHealing("//input[@id='email']");
        emailField.sendKeys("user@example.com");
         Thread.sleep(2000);
        WebElement passwordField = healingDriver.findElementWithHealing("//input[@id='passwords']");
        passwordField.sendKeys("12345");
        Thread.sleep(2000);
        WebElement button = healingDriver.findElementWithHealing("//button[@type='submitting']");
        button.click();
        Thread.sleep(2000);
        System.out.println("Clicked on submit button");
        healingDriver.closeAll();
        System.out.println("closed all browse");
    }

}