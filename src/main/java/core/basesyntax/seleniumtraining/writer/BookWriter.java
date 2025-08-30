package core.basesyntax.seleniumtraining.writer;

import core.basesyntax.seleniumtraining.model.Book;
import core.basesyntax.seleniumtraining.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookWriter implements SmartLifecycle {
    private final BookWriteQueue queue;
    private final BookService bookService;
    private volatile boolean running;

    @Override
    public void start() {
        running = true;
        Thread t = new Thread(() -> {
            while (running) {
                try {
                    List<Book> batch = queue.drainBatch(100, 200);
                    if (!batch.isEmpty()) {
                        bookService.saveAll(batch);
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    log.error("Exception while saving batch to DB: {}", e.getMessage());
                }
            }
        }, "book-writer");
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}