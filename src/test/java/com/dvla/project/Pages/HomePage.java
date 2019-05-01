package com.dvla.project.Pages;

import com.dvla.project.Utility.Utility;
import org.openqa.selenium.By;

public class HomePage extends Utility {

    private By START_NOW_BUTTON = By.cssSelector("#get-started > a");
    private String HOMEURL = "https://www.gov.uk/get-vehicle-information-from-dvla";

    public void goToHomepage() throws Throwable { goToPage(HOMEURL); }

    public void clickStartNow(){
        waitForExpectedElement(START_NOW_BUTTON).click();
    }
}
