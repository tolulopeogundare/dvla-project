package com.dvla.project.Pages;

import com.dvla.project.Utility.Utility;
import org.openqa.selenium.By;

public class VRMPage extends Utility {

    private By VRM_FIELD = By.id("Vrm");
    private By CONTINUE_BTN = By.name("Continue");

    public void inputVrm (String VRM){
        waitForExpectedElement(VRM_FIELD).sendKeys(VRM);
    }

    public void clickContinue(){
        waitForExpectedElement(CONTINUE_BTN).click();
    }
}
