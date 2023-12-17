package ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class searchPageObject extends mainPageObject {
    private static final String
    SKIP_BUTTON = "//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']",
    SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Поиск по Википедии')]",
    SEARCH_INPUT = "//*[contains(@text, 'Поиск по Википедии')]",
    SEARCH_RESULT = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']",
    ARTICLE_TITLE = "//*[@resource-id='pcs']//*[@text='{SUBSTRING}']",
    SAVE_INIT_ELEMENT = "//*[@content-desc='Сохранить']",
    ACTION_BUTTON = "//*[@text='{SUBSTRING}']",
    OK_BUTTON = "//*[@text='ОК']",
    LIST_NAME_INPUT = "//*[@text='Название этого списка']",
    ITEM_TITLE = "//*[@resource-id='org.wikipedia:id/item_title' and @text='{SUBSTRING}']",
    OVERFLOW_MENU = "//*[@resource-id='org.wikipedia:id/item_overflow_menu']",
    CLEAR_BUTTON = "//*[@resource-id='org.wikipedia:id/title' and @text='Удалить список']";
    public searchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {

        this.waitForElementAndClick(By.xpath(SKIP_BUTTON), "Не возможно найти кнопку Пропустить", 15);

        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Невозможно нажать на поле ввода", 15);

        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"Невозможно найти поле ввода", 15);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Невозможно найти поле ввода", 15);
    }

    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT.replace("{SUBSTRING}", substring);
    }

    private static String getArticleTitleElement(String substring) {
        return ARTICLE_TITLE.replace("{SUBSTRING}", substring);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Невозможно найти " + substring, 15);
    }

    public void openArticle(String substring) {

        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Невозможно найти " + substring, 15);

        String article_title_xpath = getArticleTitleElement(substring);
        WebElement title_element = this.waitForElementPresent(By.xpath(article_title_xpath), "Невозможно найти "+ substring, 15);

        String result = title_element.getText();
        Assert.assertEquals("Найдено несовпадение в названии статьи", substring, result);
    }

    public void saveArticleToRead(String listName) {
        this.waitForElementAndClick(By.xpath(SAVE_INIT_ELEMENT), "Не возможно найти кнопку сохранения", 15);

        String add_button_xpath = getActionButtonElement("Добавить в список");
        this.waitForElementAndClick(By.xpath(add_button_xpath), "Не возможно найти кнопку 'Добавить в список'", 5);

        this.waitForElementAndClear(By.xpath(LIST_NAME_INPUT), "Не возможно найти поле ввода", 15);
        this.waitForElementAndSendKeys(By.xpath(LIST_NAME_INPUT), listName, "Не возможно найти поле ввода", 15);

        this.waitForElementAndClick(By.xpath(OK_BUTTON), "Не возможно найти кнопку 'ОК'", 15);

        String open_button_xpath = getActionButtonElement("Посмотреть список");
        this.waitForElementAndClick(By.xpath(open_button_xpath), "Не возможно найти кнопку 'Посмотреть список'", 5);

        String list_title_xpath = getItemTitleElement(listName);
        WebElement title_element = this.waitForElementPresent(By.xpath(list_title_xpath), "Невозможно найти "+ listName, 15);

        String result = title_element.getText();
        Assert.assertEquals("Найдено несовпадение в названии списка", listName, result);
    }

    private static String getActionButtonElement(String substring) {
        return ACTION_BUTTON.replace("{SUBSTRING}", substring);
    }

    private static String getItemTitleElement(String substring) {
        return ITEM_TITLE.replace("{SUBSTRING}", substring);
    }

    public void clearListToRead() {
        this.waitForElementAndClick(By.xpath(OVERFLOW_MENU), "Не возможно найти дополнительное меню", 15);
        this.waitForElementAndClick(By.xpath(CLEAR_BUTTON), "Не возможно найти кнопку 'Удалить список'", 15);
        this.waitForElementAndClick(By.xpath(OK_BUTTON), "Не возможно найти кнопку 'ОК'", 15);
    }

    public void ListToReadNotPesent(String listName) {
        String list_title_xpath = getItemTitleElement(listName);
        Boolean result = this.elementIsNotPresent(list_title_xpath);
        Assert.assertTrue("Список существует на странице", result);
    }
}
