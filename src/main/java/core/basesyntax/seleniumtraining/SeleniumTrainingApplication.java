package core.basesyntax.seleniumtraining;

import core.basesyntax.seleniumtraining.scrapper.BooksToScrapeScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SeleniumTrainingApplication {

    @Autowired
    private BooksToScrapeScrapper booksToScrapeScrapper;

    public static void main(String[] args) {
        SpringApplication.run(SeleniumTrainingApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                booksToScrapeScrapper.getBooks();
            }
        };
    }
}
