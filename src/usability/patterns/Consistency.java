package usability.patterns;

import java.util.List;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.*;


import java.util.ArrayList;

public class Consistency {

    List<String> testUrls = new ArrayList<String>();
    List<List<String>> testXpaths = new ArrayList<List<String>>();
    List<WebElement> testElems = new ArrayList<WebElement>();


    private void addUrl(String url){
        testUrls.add(url);
    }

    private void addXpath(List<String> xpath){
        testXpaths.add(xpath);
    }

    public int parseConfigs(){

        try {
            File inputFile = new File("resources/consistency.xml");
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


            }
            System.out.println(testXpaths.toString());
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }



        return 0;
    }

}
