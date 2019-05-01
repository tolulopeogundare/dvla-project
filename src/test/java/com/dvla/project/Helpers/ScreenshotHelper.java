package com.dvla.project.Helpers;

import com.dvla.project.Utility.Utility;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class ScreenshotHelper extends Utility {

    private String SCREENSHOT_FOLDER = System.getProperty("user.dir") + "/screenshots";

    public void takeScreenshot(String vrm) throws IOException {
        new File(SCREENSHOT_FOLDER).mkdir();
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(SCREENSHOT_FOLDER + "/" + vrm + "_" + getDateTime().toString().replaceAll("[^0-9]", "") + ".png"));
    }

    public void doHousekeeping() throws IOException {
        FileUtils.deleteDirectory(new File(SCREENSHOT_FOLDER));
    }
}
