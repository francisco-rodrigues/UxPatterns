package com.company;
import auxiliary.DriverHandler;
import auxiliary.Gui;
import auxiliary.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import usability.patterns.Consistency;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {


//        System.setProperty("webdriver.chrome.driver",
//                "./resources/chromedriver.exe");
//
//        DriverHandler dh = new DriverHandler();


/*        ChromeDriver driver = new ChromeDriver();
        driver.get("http://demo.guru99.com/");
        WebElement elem1 = driver.findElementByXPath("//input[@name='emailid']");
        elem1.sendKeys("abc@gmail.com");

        WebElement button = driver.findElement(By.xpath("//input[@name='btnLogin']"));
        Utils.getElemEnd(button);
        System.out.println();


        button.click();*/

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui().setVisible(true);
            }
        });
//        Consistency consistency = new Consistency();
//        consistency.run();
//        DriverHandler.getDriver().quit();
//
//
//        SevenPlusTwo spt = new SevenPlusTwo();
//        spt.parseConfigs();

    }
}
