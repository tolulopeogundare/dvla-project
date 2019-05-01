package com.dvla.project.Utility;

import com.dvla.project.Files_Service.Runner;
import com.dvla.project.Helpers.WebDriverHelper;
import com.dvla.project.Pages.ConfirmVehiclePage;
import com.dvla.project.Pages.HomePage;
import com.dvla.project.Pages.VRMPage;
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
    private Runner runner;
    private HomePage homePage;
    private VRMPage vrmPage;
    private ConfirmVehiclePage confirmVehiclePage;

    protected Utility(Runner runner, HomePage homePage, VRMPage vrmPage, ConfirmVehiclePage confirmVehiclePage){
        this.driver = WebDriverHelper.getWebDriver();
        this.wait = new WebDriverWait(driver, 30);
        this.runner = runner;
        this.homePage = homePage;
        this.vrmPage = vrmPage;
        this.confirmVehiclePage = confirmVehiclePage;
    }

    public Utility(){ }

    public void goToPage(String url) throws Throwable {
        if(driver==null){ driver = getWebDriver(); }
        driver.navigate().to(url);
    }

    public Map<String, String []> retrieveAllVehicleInformation(){
        return runner.getData();
    }

    protected WebElement waitForExpectedElement(final By by){
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void verifyALl(){
       // Object[] VRMs = allVehicleData.keySet().toArray();
       // System.out.println("VRMSS = " + VRMs[0].toString());
    }

    public Map<String, String[]> findACarInformation(String VRM) throws Throwable {
        homePage.goToHomepage();
        homePage.clickStartNow();
        vrmPage.inputVrm(VRM);
        vrmPage.clickContinue();
        return confirmVehiclePage.getVehicleInformation();
    }
}
