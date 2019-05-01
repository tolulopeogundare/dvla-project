package com.dvla.project.StepsDefinition;

import com.dvla.project.FilesService.FileLoader;
import com.dvla.project.Utility.Utility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.Map;
import java.util.TreeMap;

public class VehicleVerificationSteps {

    private FileLoader fileLoader;
    private Utility utility;

    public VehicleVerificationSteps(FileLoader fileLoader, Utility utility){
        this.fileLoader = fileLoader;
        this.utility = utility;
    }

    @Given("I load all supported files in configured directory")
    public void iLoadAllSupportedFilesInConfiguredDirectory(){
        fileLoader.loadAllFiles();
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
            Assert.assertTrue(actualVehicleInformation.containsKey(VRM.toString()));
            Assert.assertEquals(expectedVehicleInformation.get(VRM)[0], actualVehicleInformation.get(VRM)[0]);
            Assert.assertEquals(expectedVehicleInformation.get(VRM)[1], actualVehicleInformation.get(VRM)[1]);
        }
    }

    @And("I get (.*) information for vehicle (.*)")
    public void IgetVehicleInformation(String Make, String VRM){
        System.out.println("VEHICLE INFO = " + fileLoader.getVehicleDetails(VRM, Make));
    }
}
