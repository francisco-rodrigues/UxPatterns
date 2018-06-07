package usability.patterns;

import auxiliary.DriverHandler;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ErrorPrevention {

    List<String> tooltipUrls = new ArrayList<String>();
    List<List<String>> tooltipXpaths = new ArrayList<List<String>>();
    List<List<String>> tooltipNames = new ArrayList<List<String>>();
    List<List<String>> tooltipIds = new ArrayList<List<String>>();
    List<List<String>> tooltipClasses = new ArrayList<List<String>>();




    public int parseConfigs(){

        try {
            File inputFile = new File("resources/errorprevention.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            System.out.println("Root element :" + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> pageList = classElement.getChildren("tooltip");
            System.out.println(pageList.size());
            System.out.println("----------------------------");

            for (int temp = 0; temp < pageList.size(); temp++) {

                Element page = pageList.get(temp);

                tooltipUrls.add(page.getChildText("url"));
                System.out.println(tooltipUrls.toString());

                List<Element> nameList = page.getChildren("name");
                List<String> nameContent = new ArrayList<String>();
                for(int i=0; i < nameList.size(); i++){

                    nameContent.add(nameList.get(i).getText());

                }

                List<Element> idList = page.getChildren("id");
                List<String> idContent = new ArrayList<String>();
                for(int i=0; i < idList.size(); i++){

                    idContent.add(idList.get(i).getText());

                }

                List<Element> classList = page.getChildren("class");
                List<String> classContent = new ArrayList<String>();
                for(int i=0; i < classList.size(); i++){

                    classContent.add(classList.get(i).getText());

                }

                List<Element> elementList = page.getChildren("element");
                List<String> elementContent = new ArrayList<String>();
                for(int i=0; i < elementList.size(); i++){

                    elementContent.add(elementList.get(i).getText());

                }

                tooltipXpaths.add(elementContent);
                tooltipNames.add(nameContent);
                tooltipIds.add(idContent);
                tooltipClasses.add(classContent);

                System.out.println();
            }

            //-----------------------------------------------------------------------------------------------//

/*
            pageList = classElement.getChildren("date");
            System.out.println(pageList.size());
            System.out.println("----------------------------");

            for (int temp = 0; temp < pageList.size(); temp++) {

                Element page = pageList.get(temp);

                addUrl(page.getChildText("url"));
                System.out.println(testUrls.toString());

                List<Element> nameList = page.getChildren("name");
                List<String> nameContent = new ArrayList<String>();
                for(int i=0; i < nameList.size(); i++){

                    nameContent.add(nameList.get(i).getText());

                }

                List<Element> idList = page.getChildren("id");
                List<String> idContent = new ArrayList<String>();
                for(int i=0; i < idList.size(); i++){

                    idContent.add(idList.get(i).getText());

                }

                List<Element> classList = page.getChildren("class");
                List<String> classContent = new ArrayList<String>();
                for(int i=0; i < classList.size(); i++){

                    classContent.add(classList.get(i).getText());

                }

                List<Element> elementList = page.getChildren("element");
                List<String> elementContent = new ArrayList<String>();
                for(int i=0; i < elementList.size(); i++){

                    elementContent.add(elementList.get(i).getText());

                }

                addXpath(elementContent);
                testNames.add(nameContent);
                testIds.add(idContent);
                testClasses.add(classContent);

                System.out.println();
            }
*/

            System.out.println(tooltipXpaths.toString());
            System.out.println(tooltipNames.toString());
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }



        return 0;
    }


    public void fetchElementsData() {


        for (int i = 0; i < tooltipUrls.size(); i++) {

            DriverHandler.getDriver().get(tooltipUrls.get(i));

            for (int j = 0; j < tooltipXpaths.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByXPath(tooltipXpaths.get(i).get(j));


                System.out.println("Tooltip:");
                String tooltip = elem.getAttribute("title");

                if(!tooltip.equals(""))
                {
                    System.out.println("Tooltip exists with the following text: " + tooltip);
                } else {

                }

            }

            for (int j = 0; j < tooltipNames.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByName(tooltipNames.get(i).get(j));


            }

            for (int j = 0; j < tooltipIds.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementById(tooltipIds.get(i).get(j));


            }

            for (int j = 0; j < tooltipClasses.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByClassName(tooltipClasses.get(i).get(j));


            }


        }
    }


}
