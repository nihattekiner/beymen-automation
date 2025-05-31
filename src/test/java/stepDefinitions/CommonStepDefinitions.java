package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import steps.BaseSteps;
import utils.ClassList;


import static constants.Constants.DEFAULT_MAX_ITERATION_COUNT;
import static constants.Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT;

public class CommonStepDefinitions {
    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Given("Go to {string} address")
    public void goToUrl(String url) {
        logger.info("Entered. Parameters; key: {}", url);
        baseSteps.switchNewWindow();
        baseSteps.goToUrl(url);
        logger.info("Current Url: {}", baseSteps.getCurrentUrl());
        baseSteps.waitForPageToCompleteState();
    }

    @Given("Check if current URL contains the value {string}")
    public void checkURLContainsRepeat(String expectedURL) {
        logger.info("Entered. Parameters; expectedURL: {}", expectedURL);
        int loopCount = 0;
        String actualURL = baseSteps.getCurrentUrl();
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {

            if (actualURL != null && actualURL.contains(expectedURL)) {
                logger.info("current URL contains the value: " + expectedURL);
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail(
                "Actual URL doesn't match the expected." + "Expected: " + expectedURL + ", Actual: "
                        + actualURL);
    }

    @Given("User wants to hover and click on the {string} category")
    public void test(String categoryName) {
        baseSteps.hoverOnTheElementAndClick(By.xpath(
                String.format("//*[text()='%s']", categoryName)
        ));
    }

    @And("Switch to new window")
    public void switchNewWindow() {
        logger.info("Entered.");
        baseSteps.switchNewWindow();
    }

    @Given("Accept Chrome alert popup")
    public void acceptChromeAlertPopup() {
        logger.info("Entered");
        baseSteps.acceptChromeAlertPopup();
    }


    @And("User waits {long} milliseconds")
    public void waitByMilliSeconds(long milliseconds) {
        logger.info("Entered. Parameters; milliseconds: {}", milliseconds);
        baseSteps.waitByMilliSeconds(milliseconds);
    }

    @And("Wait {int} seconds")
    public void waitBySeconds(int seconds) {
        logger.info("Entered. Parameters; seconds: {}", seconds);
        try {
            logger.info(seconds + " saniye bekleniyor.");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("Verify error message is displayed for wrong card number")
    public void verifyErrorMessageForInvalidCardNumber() {
        try {
            boolean isErrorDisplayed = baseSteps.isDisplayedBy(By.xpath("//div[contains(@class, 'error')]"));
            Assertions.assertTrue(isErrorDisplayed, "Error: No error message was displayed despite entering an invalid Card Number!");
            logger.info("The error message was displayed correctly for the wrong Card Number.");

        } catch (Exception e) {
            logger.error("An error occurred while checking the wrong Card Number.", e);
            throw new RuntimeException("Invalid Card Number check failed.", e);
        }
    }
}
