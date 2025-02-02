package org.apicodes;

import org.openqa.selenium.WebDriver;

import static org.apicodes.SelfHealingDriver.getOllama;


public class ResultAnalyzer{

    public static void analyzeFailure(String errorMessage) throws Exception {
       // OllamaClient ollama = new OllamaClient();
        String prompt = "What could cause this Selenium error? " + errorMessage;
        String analysis = getOllama().generateText("qwen2.5-coder:3b", prompt);
        System.out.println("AI Analysis: " + analysis);
    }
}
