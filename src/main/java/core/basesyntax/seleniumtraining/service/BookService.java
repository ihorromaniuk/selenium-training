package core.basesyntax.seleniumtraining.service;

import core.basesyntax.seleniumtraining.model.Book;
import core.basesyntax.seleniumtraining.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void saveAll(List<Book> books) {
        bookRepository.saveAll(books);
    }
}
