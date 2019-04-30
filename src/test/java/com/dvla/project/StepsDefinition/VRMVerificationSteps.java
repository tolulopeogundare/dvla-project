package com.dvla.project.StepsDefinition;

import com.dvla.project.Files_Service.Runner;
import cucumber.api.java.en.Given;

public class VRMVerificationSteps {

    private Runner runner;

    public VRMVerificationSteps(Runner runner){
        this.runner = runner;
    }

    @Given("I load all vehicle information")
    public void iLoadAllVehicleInformation(){
        runner.loadAllFiles();
    }
}
