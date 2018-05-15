package com.company;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import auxiliary.Utils;

public class Main {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",
                "./resources/chromedriver.exe");

        ChromeDriver driver=new ChromeDriver();
        driver.get("http://demo.guru99.com/");
        WebElement elem1 = driver.findElementByXPath("//input[@name='emailid']");
        elem1.sendKeys("abc@gmail.com");

        WebElement button=driver.findElement(By.xpath("//input[@name='btnLogin']"));
        Utils.getElemEnd(button);

        button.click();
    }
}
