package usability.patterns;

import java.sql.Driver;
import java.util.List;


import auxiliary.DriverHandler;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.WebElement;

import java.io.*;


import java.util.ArrayList;

public class Consistency {

    String pathname = new String();

    List<String> testUrls = new ArrayList<String>();
    List<List<String>> testXpaths = new ArrayList<List<String>>();


    public Consistency() {
        this.pathname = "resources/consistency.xml";
    }

    public Consistency(String pathname) {
        this.pathname = pathname;
    }

    private void addUrl(String url){
        testUrls.add(url);
    }

    private void addXpath(List<String> xpath){
        testXpaths.add(xpath);
    }

    public int parseConfigs(){

        try {
            File inputFile = new File(this.pathname);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            System.out.println("Root element :" + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> pageList = classElement.getChildren();
            System.out.println(pageList.size());
            System.out.println("----------------------------");

            for (int temp = 0; temp < pageList.size(); temp++) {
                Element page = pageList.get(temp);

                addUrl(page.getChildText("url"));
                System.out.println(testUrls.toString());

                List<Element> elementList = page.getChildren("element");
                List<String> elementContent = new ArrayList<String>();
                for(int i=0; i < elementList.size(); i++){

                    elementContent.add(elementList.get(i).getText());

                }

                addXpath(elementContent);
                System.out.println(elementContent.toString());
                System.out.println();


            }
            System.out.println(testXpaths.toString());
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return 0;
    }


    public void run(){

        this.parseConfigs();

        DriverHandler.getDriver().get(testUrls.get(0));
        System.out.println(testUrls.get(0));
        WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(0).get(0));
        System.out.println(elem.getAttribute("name"));

        System.out.println("begin");

        String s1 = elem.getCssValue("font-weight");
        String s2 = elem.getCssValue("background.clip");

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1);

        System.out.println("end");


    }

}
