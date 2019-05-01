package com.dvla.project.Pages;

import com.dvla.project.Utility.Utility;
import org.openqa.selenium.By;

public class HomePage extends Utility {

    private By START_NOW_BUTTON = By.cssSelector("#get-started > a");

    final String homeUrl = "https://www.gov.uk/get-vehicle-information-from-dvla";

    public void goToHomepage() throws Throwable {
        goToPage(homeUrl);
    }

    public void clickStartNow(){
        waitForExpectedElement(START_NOW_BUTTON).click();
    }
}
