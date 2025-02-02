# **Self-Healing selenium Automation locators with AI Integration** 🚀

**Overview**

This project is a Self-Healing locator demo designed to enhance the robustness of Selenium-based test automation. It leverages AI-powered locator generation using Ollama to dynamically adapt to UI changes, 
reducing maintenance overhead and improving test stability.

**Key Features**

Self-Healing Mechanism: Automatically recovers from failed locators (e.g., NoSuchElementException) by generating alternative XPath selectors.
AI-Powered Locator Generation: Uses Ollama and the qwen2.5-coder:3b model to suggest valid locators for failed elements.
Timeout Handling: Ensures AI requests terminate gracefully if they take too long.
Maven Integration: Easy dependency management and build configuration.
Extensible Design: Can be integrated into existing Selenium frameworks.

**Tech Stack**

Java: Core programming language.
Selenium WebDriver: Browser automation.
Ollama: AI for generating alternative locators.
Apache HttpClient: For making API calls to Ollama.
Gson: JSON parsing for AI responses.
Maven: Build and dependency management.

**Prerequisites**

Before running the project, ensure you have the following installed:
Java JDK 17 or higher.
Maven (for dependency management).
Ollama (installed and running locally).
Install Ollama: Ollama Installation Guide.
Pull the required model:
bash ollama pull qwen2.5-coder:3b
ChromeDriver (or any other WebDriver compatible with your browser).

**Setup Instructions**

1. Clone the Repository
git clone https://github.com/your-username/self-healing-automation-framework.git
cd self-healing-automation-framework

3. Build the Project

mvn clean install
3. Configure Ollama
Ensure the Ollama server is running:

ollama serve
4. Update Configuration
Modify the OllamaClient class to use the correct model and API endpoint (if needed).

**Usage**

Example: Self-Healing Test
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    static SelfHealingDriver healingDriver;
    public static void main(String[] args) throws Exception {
        // Set up WebDriver
        WebDriver driver = new ChromeDriver();
        healingDriver = new SelfHealingDriver(driver);

        // Navigate to the login page
        String  dir = System.getProperty("user.dir");
        System.out.println("file path "+ dir +  "/src/sample.html");
        driver.get(dir + "/src/sample.html");

        // Original locator that might fail
        String usernameLocator = "//input[@id='email']";

        // Self-healing element finding
        WebElement emailField = healingDriver.findElementWithHealing("//input[@id='email']");
        emailField.sendKeys("user@example.com");

        // Close the browser
        driver.quit();
    }
}

**How It Works**

If the original locator fails, the framework captures the HTML snippet and error details.
It sends a request to Ollama to generate alternative locators.
The framework retries the test with the new locators until it succeeds or exhausts all options.

**Customization**

1. Adjust Timeout
Modify the TIMEOUT value in the OllamaClient class to control how long the framework waits for AI responses:

java
private static final int TIMEOUT = 150000; // Timeout in milliseconds

3. Add More Models
You can use other Ollama models (e.g., llama2, mistral) by updating the model parameter in the generateText method.

4. Extend Functionality
Add support for multimodal AI (e.g., combining visual and DOM analysis).


**Acknowledgments**

Ollama for providing the AI models.
Selenium for browser automation capabilities.

**Connect with Me**

If you have any questions or suggestions, feel free to reach out:
Email: sachinbhute23nov@gmail.com
