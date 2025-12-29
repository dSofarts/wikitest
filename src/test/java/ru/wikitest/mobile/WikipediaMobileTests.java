package ru.wikitest.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.wikitest.pages.WikipediaAppPage;
import ru.wikitest.WebDriverFactory;

public class WikipediaMobileTests {

  private AndroidDriver driver;
  private WikipediaAppPage appPage;

  @BeforeMethod
  public void setup() throws Exception {
    driver = WebDriverFactory.createAndroidDriver();
    appPage = new WikipediaAppPage(driver);
    Thread.sleep(3000);
  }

  @AfterMethod
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test(priority = 1)
  public void testMainScreenSearchIsDisplayed() {
    boolean isDisplayed = appPage.isSearchContainerDisplayed();
    System.out.println("Поле поиска отображается: " + isDisplayed);
    Assert.assertTrue(isDisplayed);
  }

  @Test(priority = 2)
  public void testSearchAndOpenArticle() throws InterruptedException {
    appPage.searchForArticle("Appium");
    Thread.sleep(3000);

    String title = appPage.getArticleTitle();
    System.out.println("Заголовок статьи: '" + title + "'");

    Assert.assertTrue(title != null && !title.isEmpty());
    Assert.assertTrue(title.toLowerCase().contains("appium"),
      "Заголовок должен содержать 'Appium'. Фактический: " + title);
  }

  @Test(priority = 3)
  public void testSearchAndNavigateBack() throws InterruptedException {
    appPage.searchForArticle("Selenium");
    Thread.sleep(3000);

    String title = appPage.getArticleTitle();
    System.out.println("Открыта статья: " + title);

    appPage.navigateBack();
    Thread.sleep(2000);

    boolean isDisplayed = appPage.isSearchContainerDisplayed();
    Assert.assertTrue(isDisplayed, "После возврата должно быть видно поле поиска");
  }
}
