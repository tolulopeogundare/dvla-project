package com.dvla.project.StepsDefinition;

import com.dvla.project.Utility.Utility;
import com.dvla.project.Files_Service.Runner;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VRMVerificationSteps {

    private Runner runner;
    private Utility utility;

    public VRMVerificationSteps(Runner runner, Utility utility){
        this.runner = runner;
        this.utility = utility;
    }

    @Given("I load all supported files in configured directory")
    public void iLoadAllSupportedFilesInConfiguredDirectory(){
        runner.loadAllFiles();
    }

    @When("I retrieve all vehicles information in the files")
    public void iRetrieveAllVehiclesInformationInTheFile(){
        utility.retrieveAllVehicleInformation();
    }

    @Then("I verify each vehicle information matches what is on the website")
    public void iVerifyEachVehicleInformation(){
        utility.verifyALl();
    }


    @And("I get (.*) information for vehicle (.*)")
    public void IgetVehicleInformation(String Make, String VRM){
        System.out.println("VEHICLE INFO = " + runner.getVehicleDetails(VRM, Make));
    }
}
