package usability.patterns;

import java.util.List;
import java.io.*;


import auxiliary.DriverHandler;
import javafx.beans.binding.BooleanExpression;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.io.*;


import java.util.ArrayList;

public class Consistency {

    String pathname = new String();

    List<String> testUrls = new ArrayList<String>();
    List<List<String>> testXpaths = new ArrayList<List<String>>();

    private List<List<String>> elementsCss = new ArrayList<>();
    private List<Dimension> elementsSize = new ArrayList<>();
    private List<Point> elementsLocation = new ArrayList<>();



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

    private void setElementsSize(List<Dimension> elementsSize) {
        this.elementsSize = elementsSize;
    }

    private void setElementsCss(List<List<String>> elementsCss) {
        this.elementsCss = elementsCss;
    }

    private void setElementsLocation(List<Point> elementsLocation) {
        this.elementsLocation = elementsLocation;
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

    private void printAttributes(List<String> attributes, List<Integer> indexes){

        for(int i=0; i<indexes.size(); i++){

            System.out.println(attributes.get(indexes.get(i)));

        }

    }



    private void fetchElementsData(List<String> attributes){

        List<List<String>> allElemsCSS = new ArrayList<>();
        List<Dimension> sizes = new ArrayList<>();
        List<Point> locations = new ArrayList<>();

        for(int i=0; i<testUrls.size();i++){
            for(int j=0; j<testXpaths.get(i).size(); j++) {

                DriverHandler.getDriver().get(testUrls.get(i));
                WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(i).get(j));


                allElemsCSS.add(getElementCSSValues(attributes, elem));
                sizes.add(elem.getSize());
                locations.add(elem.getLocation());
            }
        }

        setElementsCss(allElemsCSS);
        setElementsSize(sizes);
        setElementsLocation(locations);
    }



    private List<Integer> compareCssValues(List<String> pivot, List<String> elem){

        ArrayList<Integer> diffIndexes = new ArrayList<>();


        for(int i=0; i<pivot.size();i++){
            if (pivot.get(i).equals(elem.get(i))){

            }else {diffIndexes.add(i);}
        }
        System.out.println(diffIndexes.size() + "/" + pivot.size() + " Css values differ");
        return diffIndexes;
    }

    private float sizeRatio(Dimension elem1, Dimension elem2){

        float elem1area, elem2area;
        float res;

        System.out.println(elem1);
        System.out.println(elem2);

        elem1area = elem1.getHeight() * elem1.getWidth();
        System.out.println(elem1area);
        elem2area = elem2.getHeight() * elem2.getWidth();
        System.out.println(elem2area);

        res = elem1area / elem2area;
        return res;


    }







    public void run(){

        this.parseConfigs();

        List<String> cssattr = this.parseCssAttributes();
        fetchElementsData(cssattr);


        System.out.println();
        System.out.println(elementsLocation);
        System.out.println(DriverHandler.getDriver().);
        //printAttributes(cssattr, compareCssValues(elementsCss.get(0), elementsCss.get(1)));



//        DriverHandler.getDriver().get(testUrls.get(0));
//        WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(0).get(0));
//
//
//
//        List<String> elemCSS = this.getElementCSSValues(cssattr, elem);

    }

}
