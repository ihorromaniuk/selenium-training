package core.basesyntax.seleniumtraining.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class WebDriverManager {
    public WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments(
                "--disable-gpu",
                "--window-size=1920,1080",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );
        return new ChromeDriver(options);
    }
}
