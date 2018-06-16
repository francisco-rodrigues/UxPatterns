package usability.patterns;

import java.util.Arrays;
import java.util.List;
import java.io.*;


import auxiliary.DriverHandler;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.io.*;


import java.util.ArrayList;

import java.lang.Math.*;

public class Consistency {

    String pathname = new String();

    List<String> testUrls = new ArrayList<String>();
    List<List<String>> testXpaths = new ArrayList<List<String>>();
    List<List<String>> testNames = new ArrayList<List<String>>();
    List<List<String>> testIds = new ArrayList<List<String>>();
    List<List<String>> testClasses = new ArrayList<List<String>>();


    private List<List<String>> elementsCss = new ArrayList<>();
    private List<Dimension> elementsSize = new ArrayList<>();
    private List<Point> elementsLocation = new ArrayList<>();
    private List<String> elementsTooltip = new ArrayList<>();
    private List<String> elementsPlaceholder = new ArrayList<>();




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

    public void setElementsTooltip(List<String> elementsTooltip) {
        this.elementsTooltip = elementsTooltip;
    }

    public void setElementsPlaceholder(List<String> elementsPlaceholder) {
        this.elementsPlaceholder = elementsPlaceholder;
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
                System.out.println(elementContent.toString());
                System.out.println(nameContent.toString());
                System.out.println(idContent.toString());
                System.out.println(classContent.toString());
                System.out.println();


            }
            System.out.println(testXpaths.toString());
            System.out.println(testNames.toString());
            System.out.println(testIds.toString());
            System.out.println(testClasses.toString());
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
        System.out.println();
    }

    private void fetchElementsData(List<String> attributes){

        List<List<String>> allElemsCSS = new ArrayList<>();
        List<Dimension> sizes = new ArrayList<>();
        List<Point> locations = new ArrayList<>();
        List<String> tooltips = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();

        for(int i=0; i<testUrls.size();i++){

            DriverHandler.getDriver().get(testUrls.get(i));

            for(int j=0; j<testXpaths.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(i).get(j));



                placeholders.add(findElementPlaceholder(elem));
                tooltips.add(findElementTooltip(elem));
                allElemsCSS.add(getElementCSSValues(attributes, elem));
                sizes.add(elem.getSize());
                locations.add(elem.getLocation());
            }

            for(int j=0; j<testNames.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByName(testNames.get(i).get(j));

                placeholders.add(findElementPlaceholder(elem));
                tooltips.add(findElementTooltip(elem));
                allElemsCSS.add(getElementCSSValues(attributes, elem));
                sizes.add(elem.getSize());
                locations.add(elem.getLocation());
            }

            for(int j=0; j<testIds.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementById(testIds.get(i).get(j));


                placeholders.add(findElementPlaceholder(elem));
                tooltips.add(findElementTooltip(elem));
                allElemsCSS.add(getElementCSSValues(attributes, elem));
                sizes.add(elem.getSize());
                locations.add(elem.getLocation());
            }

            for(int j=0; j<testClasses.get(i).size(); j++) {


                WebElement elem = DriverHandler.getDriver().findElementByClassName(testClasses.get(i).get(j));


                placeholders.add(findElementPlaceholder(elem));
                tooltips.add(findElementTooltip(elem));
                allElemsCSS.add(getElementCSSValues(attributes, elem));
                sizes.add(elem.getSize());
                locations.add(elem.getLocation());
            }


        }

        setElementsCss(allElemsCSS);
        setElementsSize(sizes);
        setElementsLocation(locations);
        setElementsTooltip(tooltips);
        setElementsPlaceholder(placeholders);
    }

    private String findElementTooltip(WebElement elem){

        String tooltip;

        if(elem.getTagName().equals("input")){
            if(elem.getAttribute("type").equals("text") || elem.getAttribute("type").equals("password") || elem.getAttribute("type").equals("email")){

                Actions builder = new Actions(DriverHandler.getDriver());

                tooltip = elem.getAttribute("title");


                if (tooltip.equals(""))
                {

                    Action mouseOver = builder.moveToElement(elem).build();
                    mouseOver.perform();

                    try {
                        tooltip = DriverHandler.getDriver().findElementById("tooltip").getText();
                    } catch (NoSuchElementException e) {
                        //System.out.println("FAILED TO LOCATE");
                    }

                    if (tooltip.equals("")) {
                        try {
                            tooltip = DriverHandler.getDriver().findElementByClassName("tooltip").getText();
                        } catch (NoSuchElementException e) {
                            //System.out.println("FAILED TO LOCATE");
                        }

                        if (tooltip.equals("")) {
                            try {
                                tooltip = DriverHandler.getDriver().findElementById("tooltiptext").getText();
                            } catch (NoSuchElementException e) {
                                //System.out.println("FAILED TO LOCATE");
                            }

                            if (tooltip.equals("")) {
                                try {
                                    tooltip = DriverHandler.getDriver().findElementByClassName("tooltiptext").getText();
                                } catch (NoSuchElementException e) {
                                    //System.out.println("FAILED TO LOCATE");
                                }
                                if (tooltip.equals("")) {
                                    tooltip = "not found";
                                }
                            }
                        }
                    }
                }

            }else tooltip = "invalid";
        }else tooltip = "invalid";


        return tooltip;
    }

    private String findElementPlaceholder(WebElement elem){


        String placeholder = "invalid";

        if(elem.getTagName().equals("input")) {
            if (elem.getAttribute("type").equals("text") || elem.getAttribute("type").equals("password") || elem.getAttribute("type").equals("email")) {

                placeholder = elem.getAttribute("placeholder");

                if (placeholder != null) {
                    if (placeholder.equals("")) {
                        placeholder = "not found";
                    }
                } else placeholder = "not found";

            }
        }


        return placeholder;

    }

    private void getPageHtml(String url){

        DriverHandler.getDriver().get(url);

        List<String> textCss = Arrays.asList("color", "direction", "letter-spacing", "line-height", "text-align", "text-decoration-color", "text-decoration-line", "text-decoration-style", "text-indent", "text-shadow", "text-transform", "vertical-align", "white-space", "word-spacing");

        List<WebElement> allElements = DriverHandler.getDriver().findElementsByCssSelector("*");
        List<Integer> indexList = new ArrayList<>();

        List<List<String>> cssValues = new ArrayList<>();
        List<Integer> cssIndex = new ArrayList<>();

        for(int i=0; i< allElements.size(); i++){

            WebElement e = allElements.get(i);

            String text = e.getText();
            for (WebElement child : e.findElements(By.xpath("./*"))) {
                text = text.replaceFirst(child.getText(), "");
            }

            if(!text.trim().equals("")){
                indexList.add(i);
            }

        }

        for(int i : indexList){


            List<String> elemTextCss = getElementCSSValues(textCss, allElements.get(i));


            if(cssValues.contains(elemTextCss)){

                for(int j=0; j < cssValues.size(); j++){
                    if(cssValues.get(j).equals(elemTextCss)){
                        cssIndex.add(j);
                    }
                }

            }else {
                cssValues.add(elemTextCss);
                cssIndex.add(cssValues.size() -1);
            }
        }


        for(int i=0; i< cssValues.size(); i++){
            System.out.println("Values " + i);
            for (int j =0; j< cssIndex.size(); j++){
                if(cssIndex.get(j).equals(i)){
                    System.out.println(allElements.get(indexList.get(j)).getText());
                }
            }
            System.out.println();
            for (int j=0; j< cssValues.get(i).size(); j++){
                System.out.println(textCss.get(j) + ": " + cssValues.get(i).get(j));
            }
            System.out.println("----------------------");
            System.out.println();
        }

    }


    private List<Integer> compareCssValues(List<String> pivot, List<String> elem){

        ArrayList<Integer> diffIndexes = new ArrayList<>();


        for(int i=0; i<pivot.size();i++){
            if (pivot.get(i).equals(elem.get(i))){

            }else {diffIndexes.add(i);}
        }
        System.out.println(diffIndexes.size() + "/" + pivot.size() + " Css values are different");
        System.out.println();
        return diffIndexes;
    }

    private int comparePosition(Point pivot, Point point1, boolean horizontalAlignment) {

        if(horizontalAlignment){
            return Math.abs(pivot.getY() - point1.getY());
        }else return Math.abs(pivot.getX() - point1.getX());

    }

    private float sizeRatio(Dimension elem1, Dimension elem2){

        float elem1area, elem2area;
        float res;


        elem1area = elem1.getHeight() * elem1.getWidth();

        elem2area = elem2.getHeight() * elem2.getWidth();


        res = elem1area / elem2area;
        return res;


    }

    private Dimension sizeDiff(Dimension pivot, Dimension dimension1) {


        int height = pivot.getHeight() - dimension1.getHeight();
        int width = pivot.getWidth() - dimension1.getWidth();

        return new Dimension(width,height);

    }




    private void runTestCss(int pivot, int cssPercentage, List<String> attributes){

        int index = pivot -1;

        for (int i=0; i <elementsCss.size();i++){
            if(i != index){
                System.out.println("Pivot(" + (index+1) + ") and Element " + (i+1));
                List<Integer> cssindex =  compareCssValues(elementsCss.get(index), elementsCss.get(i));
                float realPercentage = ((float)cssindex.size()/(float)attributes.size()) * 100;

                if((100-realPercentage) > cssPercentage){
                    System.out.println("CSS is similar.");
                }else{
                    printAttributes(attributes, cssindex);
                }

            }
        }

    }

    private void runTestPosition(int pivot, boolean horizontalAlignment, int positionOffset) {

        int index = pivot -1;

        for(int i=0; i<elementsLocation.size();i++){
            if(i != index){
                int diff = comparePosition(elementsLocation.get(index), elementsLocation.get(i), horizontalAlignment);
                if(diff > positionOffset){
                    System.out.println("Elements "+ (index+1) + " and " + (i+1) + " are NOT aligned " + diff + " units apart");
                }else System.out.println("Elements "+ (index+1) + " and " + (i+1) + " are aligned");

                System.out.println();
            }
        }

    }

    private void runTestSize(int pivot, boolean areaRatio, boolean dimensionDiff){

        int index = pivot -1;

        for(int i=0; i<elementsSize.size();i++) {
            if (i != index) {
                if(areaRatio){
                    System.out.println("Area ratio between elements " + (index+1) + " and " + (i+1) + " is " + sizeRatio(elementsSize.get(index), elementsSize.get(i)));
                }
                if(dimensionDiff){
                    System.out.println("Height difference between elements " + (index+1) + " and " + (i+1) + " is " + sizeDiff(elementsSize.get(index), elementsSize.get(i)).getHeight());
                    System.out.println("Width difference between elements " + (index+1) + " and " + (i+1) + " is " + sizeDiff(elementsSize.get(index), elementsSize.get(i)).getWidth());
                }
                System.out.println();
            }
        }
    }

    private void runTestTooltip(){

        for(int i=0; i<elementsTooltip.size();i++) {
            if (!elementsTooltip.get(i).equals("invalid")){
                if(elementsTooltip.get(i).equals("not found")){
                    System.out.println("Tooltip not found");
                }else System.out.println("Tooltip value: " + elementsTooltip.get(i));
            }else System.out.println("Element not analyzed for tooltips");

        }

    }

    private void runTestPlacehoder(){

        for(int i=0; i<elementsPlaceholder.size();i++) {

            if (!elementsPlaceholder.get(i).equals("invalid")){
                if(elementsPlaceholder.get(i).equals("not found")){
                    System.out.println("Placeholder not found");
                }else System.out.println("Placeholder value: " + elementsPlaceholder.get(i));
            }else System.out.println("Element not analyzed for placeholders");

        }
    }




    public void run(){

        this.parseConfigs();

        List<String> cssattr = this.parseCssAttributes();
        fetchElementsData(cssattr);


        System.out.println();
        System.out.println("CSS: ");
        printAttributes(cssattr, compareCssValues(elementsCss.get(0), elementsCss.get(1)));
        System.out.println();
        System.out.println("Position: ");
        System.out.println(elementsLocation);
        System.out.println();
        System.out.println("Size: ");
        System.out.println(sizeRatio(elementsSize.get(0), elementsSize.get(1)));



//        DriverHandler.getDriver().get(testUrls.get(0));
//        WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(0).get(0));
//
//
//
//        List<String> elemCSS = this.getElementCSSValues(cssattr, elem);

    }

    public void run(boolean css, boolean position, boolean size, boolean tooltip, int pivot, int cssPercentage, boolean horizontalAlignment, int positionOffset, boolean areaRatio, boolean dimensionDiff, boolean pagetext, String textUrl){

        this.parseConfigs();

        List<String> cssattr = this.parseCssAttributes();

        if(css || position|| size || tooltip){fetchElementsData(cssattr);}


        System.out.println();
        if(css) {
            System.out.println("CSS: ");
            runTestCss(pivot, cssPercentage, cssattr);
            System.out.println();
        }
        if(position) {
            System.out.println("Position: ");
            //System.out.println(elementsLocation);
            runTestPosition(pivot, horizontalAlignment, positionOffset);
            System.out.println();
        }
        if(size) {
            System.out.println("Size: ");
            runTestSize(pivot, areaRatio, dimensionDiff);
            //System.out.println(sizeRatio(elementsSize.get(0), elementsSize.get(1)));
        }
        if(tooltip){
            System.out.println("Tooltips: ");
            runTestTooltip();
            System.out.println();

            System.out.println("Placeholders: ");
            runTestPlacehoder();
            System.out.println();
        }
        if(pagetext){

            getPageHtml(textUrl);
        }


//        DriverHandler.getDriver().get(testUrls.get(0));
//        WebElement elem = DriverHandler.getDriver().findElementByXPath(testXpaths.get(0).get(0));
//
//
//
//        List<String> elemCSS = this.getElementCSSValues(cssattr, elem);

    }



}
