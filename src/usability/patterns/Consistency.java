package usability.patterns;

import java.util.List;
import java.io.*;


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

    private int parseConfigs(){

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

    private List<String> parseCssAttributes(){

        ArrayList<String> attr = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/css.txt"));

            String s;
            while( (s = in.readLine()) != null){
                attr.add(s);
            }
            in.close();

        }catch (FileNotFoundException e){
        }catch (IOException e){ }

        return attr;
    }

    private List<String> getElementCSSValues(List<String> attributes, WebElement element){

        ArrayList<String> values = new ArrayList<>();

        for (int i =0; i< attributes.size(); i++){


            values.add(element.getCssValue(attributes.get(i)));


        }

        return values;
    }

    private List<List<String>> getAllCssValues(List<String> attributes){

        List<List<String>> allElemsCSS = new ArrayList<>();

        for(int i=0; i<testUrls.size();i++){
            for(int j=0; j<testXpaths.get(i).size(); j++) {

                DriverHandler.getDriver().get(testUrls.get(i));
                WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(i).get(j));

                allElemsCSS.add(getElementCSSValues(attributes, elem));
            }
        }

        return allElemsCSS;
    }

    public void run(){

        this.parseConfigs();
        List<String> cssattr = this.parseCssAttributes();

        getAllCssValues(cssattr);


//        DriverHandler.getDriver().get(testUrls.get(0));
//        WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(0).get(0));
//
//
//        List<String> elemCSS = this.getElementCSSValues(cssattr, elem);

    }

}
