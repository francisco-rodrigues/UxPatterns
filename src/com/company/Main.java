package com.company;
import auxiliary.ConsistencyForm;
import auxiliary.DriverHandler;
import auxiliary.Gui;
import auxiliary.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import usability.patterns.Consistency;
import usability.patterns.ErrorPrevention;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {


//        System.setProperty("webdriver.chrome.driver",
//                "./resources/chromedriver.exe");
//
//        DriverHandler dh = new DriverHandler();




        JFrame frame = new ConsistencyForm();
        frame.setContentPane(((ConsistencyForm) frame).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

//        Consistency consistency = new Consistency();
//        consistency.run();
//        DriverHandler.getDriver().quit();
//
//
//        ErrorPrevention ep = new ErrorPrevention();
//        ep.parseConfigs();
//        ep.findTooltips();
//        DriverHandler.getDriver().quit();
//
//        SevenPlusTwo spt = new SevenPlusTwo();
//        spt.parseConfigs();

    }
}
