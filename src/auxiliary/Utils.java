package auxiliary;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class Utils {

    public static Point getElemEnd(WebElement elem){

        Point loc = elem.getLocation();
        Dimension size = elem.getSize();
        System.out.printf(loc.toString());
        System.out.printf(size.toString());

        int finalX, finalY;
        finalX = loc.getX() + size.getWidth();
        finalY= loc.getY() + size.getHeight();
        Point pt = new Point(finalX, finalY);
        System.out.printf(pt.toString());

        return pt;

    }

}
