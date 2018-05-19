package auxiliary;

import org.openqa.selenium.chrome.ChromeDriver;

public class DriverHandler {

    public static final ChromeDriver driver = new ChromeDriver();

    public static ChromeDriver getDriver() {
        return driver;
    }
}
