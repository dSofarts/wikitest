package ru.wikitest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class WikipediaPage {
  private final WebDriver driver;
  private final WebDriverWait wait;
  private static final String BASE_URL = "https://ru.wikipedia.org/wiki/Заглавная_страница";

  private final By WIKI_LOGO = By.id("p-logo");
  private final By SEARCH_INPUT = By.id("searchInput");
  private final By ARTICLE_HEADING = By.id("firstHeading");
  private final By RANDOM_PAGE_LINK = By.id("n-randompage");
  private final By BODY_CONTENT = By.id("bodyContent");

  public WikipediaPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
  }

  public boolean isMainPageContentDisplayed() {
    driver.get(BASE_URL);
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(WIKI_LOGO));
      return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(BODY_CONTENT))).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public void searchFor(String query) {
    WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
    Objects.requireNonNull(searchInput).clear();
    searchInput.sendKeys(query);
    searchInput.submit();
  }

  public String getFirstHeadingText() {
    return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_HEADING))).getText().trim();
  }

  public void clickRandomPageLink() {
    WebElement randomLink = wait.until(ExpectedConditions.elementToBeClickable(RANDOM_PAGE_LINK));
    Objects.requireNonNull(randomLink).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(BODY_CONTENT));
  }

  public boolean isSearchInputDisplayedAndEnabled() {
    WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
    return Objects.requireNonNull(searchInput).isDisplayed() && searchInput.isEnabled();
  }
}
