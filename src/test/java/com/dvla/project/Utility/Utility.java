package com.dvla.project.Utility;

import com.dvla.project.FilesService.FileLoader;
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

public class Utility extends WebDriverHelper {

    @Getter
    public WebDriver driver = WebDriverHelper.getWebDriver();
    @Getter
    public WebDriverWait wait = new WebDriverWait(driver, 30);

    private FileLoader fileLoader;
    private HomePage homePage;
    private VRMPage vrmPage;
    private ConfirmVehiclePage confirmVehiclePage;

    public Utility(FileLoader fileLoader, HomePage homePage, VRMPage vrmPage, ConfirmVehiclePage confirmVehiclePage){
        this.fileLoader = fileLoader;
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
        return fileLoader.getData();
    }

    public WebElement waitForExpectedElement(final By by){
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public Map<String, String[]> findACarInformation(String VRM) throws Throwable {
        homePage.goToHomepage();
        homePage.clickStartNow();
        vrmPage.inputVrm(VRM);
        vrmPage.clickContinue();
        return confirmVehiclePage.getVehicleInformation();
    }
}
