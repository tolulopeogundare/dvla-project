package com.dvla.project.StepsDefinition;

import com.dvla.project.FilesService.FileLoader;
import com.dvla.project.Helpers.ScreenshotHelper;
import com.dvla.project.Utility.Utility;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class VehicleVerificationSteps {

    private FileLoader fileLoader;
    private Utility utility;
    private ScreenshotHelper screenshotHelper;

    public VehicleVerificationSteps(FileLoader fileLoader, Utility utility, ScreenshotHelper screenshotHelper){
        this.fileLoader = fileLoader;
        this.utility = utility;
        this.screenshotHelper = screenshotHelper;
    }

    @Before
    public void houseKeeping() throws IOException {
        screenshotHelper.doHousekeeping();
    }

    @Given("I load all supported files in configured directory")
    public void iLoadAllSupportedFilesInConfiguredDirectory() throws IOException {
        fileLoader.readAllFiles();
    }

    @When("I retrieve all vehicles information in the files")
    public void iRetrieveAllVehiclesInformationInTheFile(){
        utility.retrieveAllVehicleInformation();
    }

    @Then("I verify each vehicle information matches what is on the website")
    public void iVerifyEachVehicleInformation() throws Throwable {
        Map<String, String[]> vehicles = utility.retrieveAllVehicleInformation();
        Object [] VRMs = vehicles.keySet().toArray();
        for(Object VRM : VRMs){
            Map<String, String[]> expectedVehicleInformation = new TreeMap<>();
            Map<String, String[]> actualVehicleInformation = utility.findACarInformation(VRM.toString());
            expectedVehicleInformation.put(VRM.toString(), vehicles.get(VRM.toString()));

            screenshotHelper.takeScreenshot(VRM.toString());

            Assert.assertTrue(actualVehicleInformation.containsKey(VRM.toString()));
            Assert.assertEquals(expectedVehicleInformation.get(VRM.toString())[0], actualVehicleInformation.get(VRM.toString())[0]);
            Assert.assertEquals(expectedVehicleInformation.get(VRM.toString())[1], actualVehicleInformation.get(VRM.toString())[1]);
        }
    }
}