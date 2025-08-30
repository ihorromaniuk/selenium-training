package core.basesyntax.seleniumtraining.writer;

import core.basesyntax.seleniumtraining.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class BookWriteQueue {
    private final BlockingQueue<Book> queue = new LinkedBlockingQueue<>();

    public void enqueue(Book b) {
        queue.add(b);
    }

    public List<Book> drainBatch(int max, long waitMillis) throws InterruptedException {
        List<Book> batch = new ArrayList<>(max);

        Book first = queue.poll(waitMillis, TimeUnit.MILLISECONDS);
        if (first != null) {
            batch.add(first);
        }

        queue.drainTo(batch, max - batch.size());
        return batch;
    }
}