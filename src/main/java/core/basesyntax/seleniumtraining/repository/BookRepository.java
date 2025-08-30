package core.basesyntax.seleniumtraining.repository;

import core.basesyntax.seleniumtraining.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
