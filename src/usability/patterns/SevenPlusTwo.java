package usability.patterns;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SevenPlusTwo {


    List<String> testUrls = new ArrayList<String>();
    List<String> testXpaths = new ArrayList<String>();
    List<WebElement> testElems = new ArrayList<WebElement>();


    private void addUrl(String url){
        testUrls.add(url);
    }

    private void addXpath(String xpath){
        testXpaths.add(xpath);
    }

    public int parseConfigs(){

        try {
            File inputFile = new File("resources/sevenplustwo.xml");
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

                Element menu = page.getChild("menu");
                String menuItemContent;
                menuItemContent = menu.getText();



                addXpath(menuItemContent);
                System.out.println(menuItemContent.toString());
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

}
