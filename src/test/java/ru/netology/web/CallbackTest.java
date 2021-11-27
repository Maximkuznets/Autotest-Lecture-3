package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestList() {
        driver.get("http://localhost:9999/");

        List<WebElement> textField = driver.findElements(By.className("input__control"));

        textField.get(0).sendKeys("Максим");
        textField.get(1).sendKeys("+79057453467");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actual = driver.findElement(By.className("paragraph")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestCelector() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иван");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79057455396");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector(".paragraph")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestInncorrectData() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("879057455396");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector(".input_invalid")).getText().trim();

        String expected = "Мобильный телефон\n" +
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";


        assertEquals(expected, actual);
    }


}

