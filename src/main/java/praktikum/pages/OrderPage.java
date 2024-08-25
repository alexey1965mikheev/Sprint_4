package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static praktikum.EnvConfig.EXPLICIT_WAIT;
import static praktikum.EnvConfig.IMPLICIT_WAIT;

public class OrderPage {
    private final WebDriver driver;

    //Локаторы полей ввода
    private By firstNameField = By.cssSelector("[placeholder='* Имя']");
    private By lastNameField = By.cssSelector("[placeholder='* Фамилия']");
    private By addressField = By.cssSelector("[placeholder='* Адрес: куда привезти заказ']");
    private By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By deliveryDateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private By rentalTimeField = By.xpath(".//div[@class='Dropdown-placeholder']");
    private By commentField = By.cssSelector("[placeholder='Комментарий для курьера']");

    //Локатор кнопки "Далее"
    private By nextButton = By.xpath(".//button[text()='Далее']");
    //Локатор кнопки "Заказать" на странице подтверждения заказа
    private By placeAnOrderButton = By.xpath(".//button[text()='Назад']/parent::div/button[text()='Заказать']");
    //Локатор кнопки "Да" в окне подтверждения заказа
    private By yesButton = By.xpath(".//button[text()='Да']");

    //локатор для окна "Заказ оформлен"
    private final By orderIsProcessed = By.xpath(".//div[text()='Заказ оформлен']");

        public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Ввести имя пользователя
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    //Ввести фамилиию пользователя
    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    //Ввести адрес пользователя
    public void enterAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    //Ввести станцию метро
    public void selectMetroStation(String metroStation) {
        driver.findElement(metroStationField).sendKeys(metroStation);
        waitMetroIsVisible(metroStation);
        containsText(metroStation).click();
    }

    //Ввести телефон пользователя
    public void enterPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    //Нажать на кнопку "Далее" для перехода на следующую страницу
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    //Шаг заполнения персональных данных пользователя на странице "Для кого самокат" и перехода
    public OrderPage fillWhoIsScooterForPage(String firstName, String lastName, String address, String metroStation, String phone) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterAddress(address);
        selectMetroStation(metroStation);
        enterPhone(phone);
        clickNextButton();
        return this;
    }

    //Ввести дату доставки самоката
    public void enterDeliveryDate(String deliveryDate) {
        driver.findElement(deliveryDateField).sendKeys(deliveryDate);
        containsText("5").click();
    }

    //Выбрать срок аренды из выпадающего списка
    public void selectRentalTime(String rentalTime) {
        driver.findElement(rentalTimeField).click();
        containsText(rentalTime).click();
    }

    //Заполнить поле ввода "Комментарии" (необязательное)
    public void enterComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    //Шаг заполнения данных о заказе в форме страницы "Про аренду"
    public OrderPage fillAboutRentalPage(String deliveryDate, String rentalTime, String scooterColour, String comment) {
        enterDeliveryDate(deliveryDate);
        selectRentalTime(rentalTime);
        enterComment(comment);
        clickOnPlaceAnOrderButton();
        return this;
    }
    //Нажать на кнопку "Заказать"
    public OrderPage clickOnPlaceAnOrderButton() {
        driver.findElement(placeAnOrderButton).click();
        return this;
    }

    //Нажать на кнопку "Да"
    public OrderPage clickOnYesButton() {
        driver.findElement(yesButton).click();
        return this;
    }

    public boolean orderIsProcessedIsVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(orderIsProcessed));
        return driver.findElement(orderIsProcessed).isDisplayed();
    }

    private WebElement containsText(String text) {
        return driver.findElement(By.xpath(".//*[contains(text(),'" + text + "')]"));
    }
    private void waitMetroIsVisible(String metroStation) {
        new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text(),'" + metroStation + "')]")));
    }
}
