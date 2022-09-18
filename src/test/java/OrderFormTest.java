import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderFormTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithSpaces() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithoutSpaces() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("ГудманСол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithDashesAndWithoutSpaces() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гуд-манСол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithSpacesAndDashes() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Лингстад Анни-Фрид");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+46812132860");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldFailWhenNameIsEmpty() {

        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenNameContainsLatin() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Goodman Saul");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenNameContainsMarks() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenNameContainsNumbers() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман С0л");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneIsEmpty() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneStartsWithoutPlus() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("15058425662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneContainsMoreThan11() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+150584256662");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneContainsLessThan11() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+1");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneContainsLetters() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+1BetterCаll");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenPhoneContainsMarks() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+111-111-111");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldFailWhenNoCheck() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }

    @Test
    void shouldOnlyShowCaptionUnderNameWhenNameAndPhoneBothInvalid() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("kidNamedFinger");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid"));
        });
    }

    @Test
    void shouldOnlyShowCaptionUnderPhoneWhenPhoneAndCheckboxBothInvalid() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("kidNamedFinger");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid")).isDisplayed());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        });
    }

    @Test
    void shouldOnlyShowCaptionUnderNameWhenNameAndCheckboxBothInvalid() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        });
    }

    @Test
    void shouldOnlyShowCaptionUnderNameWhenAllValuesInvalid() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("kidNamedFinger");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        });
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid"));
        });
    }
}