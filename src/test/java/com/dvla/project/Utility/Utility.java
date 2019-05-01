package com.dvla.project.Utility;

import com.dvla.project.Files_Service.Runner;
import com.dvla.project.Helpers.WebDriverHelper;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;
import java.util.TreeMap;

public class Utility extends WebDriverHelper {

    @Getter
    protected WebDriverWait wait;
    @Getter
    protected WebDriver driver;

    protected Utility(){
        this.driver = WebDriverHelper.getWebDriver();
        this.wait = new WebDriverWait(driver, 30);
    }

    public Utility(Runner runner){
        this.runner = runner;
    }

    public void goToPage(String url) throws Throwable {
        if(driver==null){ driver = getWebDriver(); }
        driver.navigate().to(url);
    }

    private Runner runner;
    private Map<String, String []> data = new TreeMap<>();

    public void retrieveAllVehicleInformation(){
        data = runner.getData();
    }

    protected WebElement waitForExpectedElement(final By by){
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void verifyALl(){
       Object[] VRMs = data.keySet().toArray();
       System.out.println("VRMSS = " + VRMs[0].toString());
    }
}
