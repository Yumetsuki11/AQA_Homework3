import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderFormTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithSpaces() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("p")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithoutSpaces() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("ГудманСол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("p")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithDashesAndWithoutSpaces() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гуд-манСол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("p")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldSubmitWhenAllFieldsFilledCorrectly_NameWithSpacesAndDashes() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Лингстад Анни-Фрид");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+46812132860");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("p")).getText().trim();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldFailWhenNameIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenNameContainsLatin() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Goodman Saul");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenNameContainsMarks() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenNameContainsNumbers() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман С0л");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenPhoneIsEmpty() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenPhoneStartsWithoutPlus() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("15058425662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenPhoneContainsMoreThan11() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+150584256662");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenPhoneContainsLessThan11() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+1");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenPhoneContainsLetters() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+1BetterCаll");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenPhoneContainsMarks() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+111-111-111");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldFailWhenNoCheck() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("label[class='checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid']")).size() != 0);
    }

    @Test
    void shouldOnlyShowCaptionUnderNameWhenNameAndPhoneBothInvalid() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("kidNamedFinger");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
        Assertions.assertFalse(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }

    @Test
    void shouldOnlyShowCaptionUnderPhoneWhenPhoneAndCheckboxBothInvalid() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("kidNamedFinger");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
        Assertions.assertFalse(driver.findElements(By.cssSelector("label[class='checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid']")).size() != 0);
    }

    @Test
    void shouldOnlyShowCaptionUnderNameWhenNameAndCheckboxBothInvalid() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("+15058425662");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
        Assertions.assertFalse(driver.findElements(By.cssSelector("label[class='checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid']")).size() != 0);
    }

    @Test
    void shouldOnlyShowCaptionUnderNameWhenAllValuesInvalid() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".input__control[type=\"text\"]")).sendKeys("Гудман, Сол");
        driver.findElement(By.cssSelector(".input__control[type=\"tel\"]")).sendKeys("kidNamedFinger");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        Assertions.assertTrue(driver.findElements(By.cssSelector("span[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
        Assertions.assertFalse(driver.findElements(By.cssSelector("label[class='checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid']")).size() != 0);
        Assertions.assertFalse(driver.findElements(By.cssSelector("span[class='input input_type_tel input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).size() != 0);
    }
}