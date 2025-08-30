package core.basesyntax.seleniumtraining.scrapper;

import core.basesyntax.seleniumtraining.model.Book;
import core.basesyntax.seleniumtraining.util.WebDriverManager;
import core.basesyntax.seleniumtraining.writer.BookWriteQueue;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BooksToScrapeScrapper {
    private static final String SITE_URL = "https://books.toscrape.com/";
    private static final Integer THREAD_AMOUNT = 10;

    private final WebDriverManager driverManager;
    private final BookWriteQueue bookWriteQueue;

    public void getBooks() {
        WebDriver driver = driverManager.create();
        driver.get(SITE_URL);
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_AMOUNT);
        while (true) {
            driver.findElements(By.cssSelector(".product_pod h3 a")).stream()
                    .map(e -> e.getAttribute("href"))
                    .forEach(url -> {
                        pool.submit(() -> {
                            WebDriver webDriver = driverManager.create();
                            try {
                                webDriver.get(url);
                                Book book = new Book();
                                book.setId(webDriver
                                        .findElement(By.cssSelector(".table-striped>tbody>tr>td"))
                                        .getText());
                                book.setTitle(webDriver.
                                        findElement(By.cssSelector(".product_main>h1")).getText());
                                book.setImageUrl(webDriver
                                        .findElement(By.cssSelector(".carousel-inner>div>img"))
                                        .getAttribute("src"));
                                book.setPrice(BigDecimal.valueOf(Double.parseDouble(webDriver
                                        .findElement(By.cssSelector(".price_color"))
                                        .getText().substring(1))));
                                book.setDescription(webDriver
                                        .findElement(By.cssSelector(".product_page>p")).getText());
                                bookWriteQueue.enqueue(book);
                            } finally {
                                webDriver.quit();
                            }
                        });
                    });
            try {
                driver.findElement(By.cssSelector(".next a")).click();
            } catch (NoSuchElementException e) {
                driver.quit();
                pool.shutdown();
                try {
                    pool.awaitTermination(5, TimeUnit.MINUTES);
                } catch (InterruptedException ex) {
                    log.error("Exception occurred while terminating thread pool: {}",
                            ex.getMessage());
                }
                log.info("Book scraping done");
                break;
            }
        }
    }
}
