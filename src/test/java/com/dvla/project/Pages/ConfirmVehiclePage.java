package com.dvla.project.Pages;

import com.dvla.project.Utility.Utility;
import org.openqa.selenium.By;

import java.util.Map;
import java.util.TreeMap;

public class ConfirmVehiclePage extends Utility {

    private By VRM = By.className("reg-mark");
    private By MAKE = By.cssSelector("#pr3 > div > ul > li:nth-child(2) > span:nth-child(2) > strong");
    private By COLOUR = By.cssSelector("#pr3 > div > ul > li:nth-child(3) > span:nth-child(2) > strong");

    public Map<String, String[]> getVehicleInformation(){
        final String vehicleVRM = waitForExpectedElement(VRM).getText().trim().replace(" ", "");
        final String vehicleMake = waitForExpectedElement(MAKE).getText().trim();
        final String vehicleColour = waitForExpectedElement(COLOUR).getText().trim();
        Map<String, String[]> info = new TreeMap<>();
        String[] data = new String[2];
        data[0] = vehicleMake;
        data[1] = vehicleColour;
        info.put(vehicleVRM, data);
        return info;
    }
}
