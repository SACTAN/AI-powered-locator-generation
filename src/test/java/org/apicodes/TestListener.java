package org.apicodes;

import org.testng.ITestListener;
import org.testng.ITestResult;

import static org.apicodes.SelfHealingDriver.getOllama;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        String errorLog = result.getThrowable().getMessage();
        String analysisPrompt = "Analyze this Selenium test failure: " + errorLog +
                ". Suggest possible fixes in bullet points.";

        String aiAnalysis = null;
        try {
            aiAnalysis = getOllama().generateText("qwen2.5-coder:3b", analysisPrompt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("AI Failure Analysis:\n" + aiAnalysis);
    }
}
