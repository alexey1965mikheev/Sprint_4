package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;
import java.util.List;

import static praktikum.EnvConfig.IMPLICIT_WAIT;

public class MainPage {
    private final WebDriver driver;

    //локатор раздела вопросов
    private final By questionsLabel = By.xpath(".//div[text()='Вопросы о важном']");
    //локатор кнопки куки
    private final By acceptCookiesButton = By.id("rcc-confirm-button");
    //локатор заголовка вопроса
    private final By listAccordionHeaders = By.xpath(".//div[@class='accordion__item']");
    //локатор ответа на вопрос
    private final By listAccordionItems = By.xpath(".//div[@class='accordion__item']//p");

    //локатор кнопок заказа
    private final By orderButtons = By.xpath(".//*[contains(@class,'ra12g') and text()='Заказать']");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Открыть домашнюю страницу
    public MainPage open() {
        driver.get(EnvConfig.BASE_URL);
        return this;
    }

    //Принять куки, если отображается окно
    public MainPage acceptCookies() {
        if (driver.findElement(acceptCookiesButton).isDisplayed()) {
            driver.findElement(acceptCookiesButton).click();
        }
        return this;
    }


    //получить вопросы
    public List<WebElement> getQuestions() {
        return driver.findElements(listAccordionHeaders);
    }

    //получить ответы
    public List<WebElement> getAnswers() {
        return driver.findElements(listAccordionItems);
    }

   //дождаться загрузки вопроса
    public MainPage waitForQuestion(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOf(getQuestions().get(index)));
        return this;
    }

    //скролл до вопросов
    public MainPage scrollToQuestionList() {
        WebElement element = driver.findElement(questionsLabel);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        return this;
    }

    //кликнуть на вопрос
    public MainPage clickOnQuestion(int index) {
        getQuestions().get(index).click();
        return this;
    }

    //подождать, пока загрузится вопрос
    public MainPage waitForAnswer(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOf(getAnswers().get(index)));
        return this;
    }

    //получить текст ответа
    public String getAnswerText(int index) {
        return getAnswers().get(index).getText();
    }

    //скролл до кнопки "заказать"(0-в заголовке, 1-в футере)
    public OrderPage clickOnOrderButton(int index) {
        WebElement element = driver.findElements(orderButtons).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
        return new OrderPage(driver);
    }
}
