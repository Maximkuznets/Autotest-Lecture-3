package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    public void shouldTestCelector() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79057455396");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }

    // --------------- тестируем валидацию полей ------------------------------

    @Test
    public void shouldTestInncorrectName() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79057455396");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual);
    }


    @Test
    public void shouldTestInncorrectTel() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Андрей");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("879057455396");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestNoClickCheckbox() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Андрей");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79057455396");

        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestSendEmplyForm() {
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestSendEmplyFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79057455396");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTestSendEmplyFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Андрей");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

}

