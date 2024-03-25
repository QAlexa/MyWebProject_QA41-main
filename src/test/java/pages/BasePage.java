package pages;

import config.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// BasePage служит базовым классом для всех страниц тестового приложения
// BasePage служит базовым классом для всех страниц тестового приложения
public class BasePage {

    protected static WebDriver driver;
    public static void setDriver(WebDriver webDriver){ // Метод устанавливает значение поля driver в переданный экземпляр веб-драйвера.
        driver=webDriver; //Эта строка присваивает переданный экземпляр веб-драйвера переменной driver, что позволяет другим классам иметь доступ к этому веб-драйверу через метод getDriver()
    }
}