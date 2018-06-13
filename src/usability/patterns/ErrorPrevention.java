package usability.patterns;

import auxiliary.DriverHandler;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

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

    List<String> textboxUrls = new ArrayList<String>();
    List<List<String>> textboxXpaths = new ArrayList<List<String>>();
    List<List<String>> textboxNames = new ArrayList<List<String>>();
    List<List<String>> textboxIds = new ArrayList<List<String>>();
    List<List<String>> textboxClasses = new ArrayList<List<String>>();


    public int parseConfigs() {

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
                for (int i = 0; i < nameList.size(); i++) {

                    nameContent.add(nameList.get(i).getText());

                }

                List<Element> idList = page.getChildren("id");
                List<String> idContent = new ArrayList<String>();
                for (int i = 0; i < idList.size(); i++) {

                    idContent.add(idList.get(i).getText());

                }

                List<Element> classList = page.getChildren("class");
                List<String> classContent = new ArrayList<String>();
                for (int i = 0; i < classList.size(); i++) {

                    classContent.add(classList.get(i).getText());

                }

                List<Element> elementList = page.getChildren("element");
                List<String> elementContent = new ArrayList<String>();
                for (int i = 0; i < elementList.size(); i++) {

                    elementContent.add(elementList.get(i).getText());

                }

                tooltipXpaths.add(elementContent);
                tooltipNames.add(nameContent);
                tooltipIds.add(idContent);
                tooltipClasses.add(classContent);

                System.out.println();
            }

            //-----------------------------------------------------------------------------------------------//


            pageList = classElement.getChildren("textbox");
            System.out.println(pageList.size());
            System.out.println("----------------------------");

            for (int temp = 0; temp < pageList.size(); temp++) {

                Element page = pageList.get(temp);

                textboxUrls.add(page.getChildText("url"));
                System.out.println(textboxUrls.toString());

                List<Element> nameList = page.getChildren("name");
                List<String> nameContent = new ArrayList<String>();
                for (int i = 0; i < nameList.size(); i++) {

                    nameContent.add(nameList.get(i).getText());

                }

                List<Element> idList = page.getChildren("id");
                List<String> idContent = new ArrayList<String>();
                for (int i = 0; i < idList.size(); i++) {

                    idContent.add(idList.get(i).getText());

                }

                List<Element> classList = page.getChildren("class");
                List<String> classContent = new ArrayList<String>();
                for (int i = 0; i < classList.size(); i++) {

                    classContent.add(classList.get(i).getText());

                }

                List<Element> elementList = page.getChildren("element");
                List<String> elementContent = new ArrayList<String>();
                for (int i = 0; i < elementList.size(); i++) {

                    elementContent.add(elementList.get(i).getText());

                }

                textboxXpaths.add(elementContent);
                textboxNames.add(nameContent);
                textboxIds.add(idContent);
                textboxClasses.add(classContent);

                System.out.println();
            }


            System.out.println(tooltipXpaths.toString());
            System.out.println(tooltipNames.toString());
            System.out.println(tooltipIds.toString());
            System.out.println(tooltipClasses.toString());

            System.out.println(textboxXpaths.toString());
            System.out.println(textboxNames.toString());
            System.out.println(textboxIds.toString());
            System.out.println(textboxClasses.toString());
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return 0;
    }


    public void findTooltips() {


        for (int i = 0; i < tooltipUrls.size(); i++) {

            DriverHandler.getDriver().get(tooltipUrls.get(i));
            Actions builder = new Actions(DriverHandler.getDriver());


            for (int j = 0; j < tooltipXpaths.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByXPath(tooltipXpaths.get(i).get(j));


                String tooltip = elem.getAttribute("title");

                String placeholder = elem.getAttribute("placeholder");

                System.out.println();
                System.out.println();
                System.out.println("Placeholder:");

                if(placeholder != null) {
                    if (!placeholder.equals("")) {
                        System.out.println("Placeholder exists with value: " + placeholder);
                    } else System.out.println("No placeholder found");
                }else System.out.println("No placeholder found");

                System.out.println();
                System.out.println("Tooltip:");


                if (!tooltip.equals("")) {
                    System.out.println("Tooltip exists with the following text: " + tooltip);
                } else {

                    Action mouseOver = builder.moveToElement(elem).build();
                    mouseOver.perform();

                    try {
                        tooltip = DriverHandler.getDriver().findElementById("tooltip").getText();
                    } catch (NoSuchElementException e) {
                        System.out.println("FAILED TO LOCATE");
                    }

                    if (!tooltip.equals("")) {
                        System.out.println("Tooltip exists with the following text: " + tooltip);
                    } else {

                        try {
                            tooltip = DriverHandler.getDriver().findElementByClassName("tooltip").getText();
                        } catch (NoSuchElementException e) {
                            System.out.println("FAILED TO LOCATE");
                        }

                        if (!tooltip.equals("")) {
                            System.out.println("Tooltip exists with the following text: " + tooltip);
                        } else {

                            try {
                                tooltip = DriverHandler.getDriver().findElementById("tooltiptext").getText();
                            } catch (NoSuchElementException e) {
                                System.out.println("FAILED TO LOCATE");
                            }

                            if (!tooltip.equals("")) {
                                System.out.println("Tooltip exists with the following text: " + tooltip);
                            } else {

                                try {
                                    tooltip = DriverHandler.getDriver().findElementByClassName("tooltiptext").getText();
                                } catch (NoSuchElementException e) {
                                    System.out.println("FAILED TO LOCATE");
                                }
                                if (!tooltip.equals("")) {
                                    System.out.println("Tooltip exists with the following text: " + tooltip);
                                } else {
                                    System.out.println("A tooltip was not found");
                                }
                            }
                        }
                    }
                }


            }

            for (int j = 0; j < tooltipNames.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByName(tooltipNames.get(i).get(j));


                String tooltip = elem.getAttribute("title");


                String placeholder = elem.getAttribute("placeholder");


                System.out.println();
                System.out.println();
                System.out.println("Placeholder:");

                if(placeholder != null) {
                    if (!placeholder.equals("")) {
                        System.out.println("Placeholder exists with value: " + placeholder);
                    } else System.out.println("No placeholder found");
                }else System.out.println("No placeholder found");

                System.out.println();
                System.out.println("Tooltip:");

                if (!tooltip.equals("")) {
                    System.out.println("Tooltip exists with the following text: " + tooltip);
                } else {

                    Action mouseOver = builder.moveToElement(elem).build();
                    mouseOver.perform();

                    try {
                        tooltip = DriverHandler.getDriver().findElementById("tooltip").getText();
                    } catch (NoSuchElementException e) {
                        System.out.println("FAILED TO LOCATE");
                    }

                    if (!tooltip.equals("")) {
                        System.out.println("Tooltip exists with the following text: " + tooltip);
                    } else {

                        try {
                            tooltip = DriverHandler.getDriver().findElementByClassName("tooltip").getText();
                        } catch (NoSuchElementException e) {
                            System.out.println("FAILED TO LOCATE");
                        }

                        if (!tooltip.equals("")) {
                            System.out.println("Tooltip exists with the following text: " + tooltip);
                        } else {

                            try {
                                tooltip = DriverHandler.getDriver().findElementById("tooltiptext").getText();
                            } catch (NoSuchElementException e) {
                                System.out.println("FAILED TO LOCATE");
                            }

                            if (!tooltip.equals("")) {
                                System.out.println("Tooltip exists with the following text: " + tooltip);
                            } else {

                                try {
                                    tooltip = DriverHandler.getDriver().findElementByClassName("tooltiptext").getText();
                                } catch (NoSuchElementException e) {
                                    System.out.println("FAILED TO LOCATE");
                                }
                                if (!tooltip.equals("")) {
                                    System.out.println("Tooltip exists with the following text: " + tooltip);
                                } else {
                                    System.out.println("A tooltip was not found");
                                }
                            }
                        }
                    }
                }

            }

            for (int j = 0; j < tooltipIds.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementById(tooltipIds.get(i).get(j));


                String tooltip = elem.getAttribute("title");

                String placeholder = elem.getAttribute("placeholder");


                System.out.println();
                System.out.println();
                System.out.println("Placeholder:");

                if(placeholder != null) {
                    if (!placeholder.equals("")) {
                        System.out.println("Placeholder exists with value: " + placeholder);
                    } else System.out.println("No placeholder found");
                }else System.out.println("No placeholder found");

                System.out.println();
                System.out.println("Tooltip:");

                if (!tooltip.equals("")) {
                    System.out.println("Tooltip exists with the following text: " + tooltip);
                } else {

                    Action mouseOver = builder.moveToElement(elem).build();
                    mouseOver.perform();

                    try {
                        tooltip = DriverHandler.getDriver().findElementById("tooltip").getText();
                    } catch (NoSuchElementException e) {
                        System.out.println("FAILED TO LOCATE");
                    }

                    if (!tooltip.equals("")) {
                        System.out.println("Tooltip exists with the following text: " + tooltip);
                    } else {

                        try {
                            tooltip = DriverHandler.getDriver().findElementByClassName("tooltip").getText();
                        } catch (NoSuchElementException e) {
                            System.out.println("FAILED TO LOCATE");
                        }

                        if (!tooltip.equals("")) {
                            System.out.println("Tooltip exists with the following text: " + tooltip);
                        } else {

                            try {
                                tooltip = DriverHandler.getDriver().findElementById("tooltiptext").getText();
                            } catch (NoSuchElementException e) {
                                System.out.println("FAILED TO LOCATE");
                            }

                            if (!tooltip.equals("")) {
                                System.out.println("Tooltip exists with the following text: " + tooltip);
                            } else {

                                try {
                                    tooltip = DriverHandler.getDriver().findElementByClassName("tooltiptext").getText();
                                } catch (NoSuchElementException e) {
                                    System.out.println("FAILED TO LOCATE");
                                }
                                if (!tooltip.equals("")) {
                                    System.out.println("Tooltip exists with the following text: " + tooltip);
                                } else {
                                    System.out.println("A tooltip was not found");
                                }
                            }
                        }
                    }
                }

            }

            for (int j = 0; j < tooltipClasses.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByClassName(tooltipClasses.get(i).get(j));

                String tooltip = elem.getAttribute("title");

                String placeholder = elem.getAttribute("placeholder");


                System.out.println();
                System.out.println();
                System.out.println("Placeholder:");

                if(placeholder != null) {
                    if (!placeholder.equals("")) {
                        System.out.println("Placeholder exists with value: " + placeholder);
                    } else System.out.println("No placeholder found");
                }else System.out.println("No placeholder found");

                System.out.println();
                System.out.println("Tooltip:");
                if (!tooltip.equals("")) {
                    System.out.println("Tooltip exists with the following text: " + tooltip);
                } else {

                    Action mouseOver = builder.moveToElement(elem).build();
                    mouseOver.perform();

                    try {
                        tooltip = DriverHandler.getDriver().findElementById("tooltip").getText();
                    } catch (NoSuchElementException e) {
                        System.out.println("FAILED TO LOCATE");
                    }

                    if (!tooltip.equals("")) {
                        System.out.println("Tooltip exists with the following text: " + tooltip);
                    } else {

                        try {
                            tooltip = DriverHandler.getDriver().findElementByClassName("tooltip").getText();
                        } catch (NoSuchElementException e) {
                            System.out.println("FAILED TO LOCATE");
                        }

                        if (!tooltip.equals("")) {
                            System.out.println("Tooltip exists with the following text: " + tooltip);
                        } else {

                            try {
                                tooltip = DriverHandler.getDriver().findElementById("tooltiptext").getText();
                            } catch (NoSuchElementException e) {
                                System.out.println("FAILED TO LOCATE");
                            }

                            if (!tooltip.equals("")) {
                                System.out.println("Tooltip exists with the following text: " + tooltip);
                            } else {

                                try {
                                    tooltip = DriverHandler.getDriver().findElementByClassName("tooltiptext").getText();
                                } catch (NoSuchElementException e) {
                                    System.out.println("FAILED TO LOCATE");
                                }
                                if (!tooltip.equals("")) {
                                    System.out.println("Tooltip exists with the following text: " + tooltip);
                                } else {
                                    System.out.println("A tooltip was not found");
                                }
                            }
                        }
                    }
                }

            }


        }
    }


}
