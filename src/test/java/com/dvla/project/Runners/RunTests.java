package com.dvla.project.Runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( plugin ={"pretty", "html:target/cucumber", "junit:target/cucumber.xml"},
                features = "src/test/java/features",
                glue = {"com.dvla.project.StepsDefinition"}
                )
public class RunTests {
}
