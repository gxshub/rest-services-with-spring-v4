package csci318.demo.service;

import csci318.demo.model.Book;
import csci318.demo.model.Library;
import csci318.demo.model.event.BookEvent;
import csci318.demo.repository.BookRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    BookService(BookRepository bookRepository, RestTemplate restTemplate,
                ApplicationEventPublisher applicationEventPublisher){
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(String isbn) {
        return bookRepository.findById(isbn).orElseThrow(RuntimeException::new);
    }

    public List<Long> getAvailableLibraries1(String isbn) {
        return bookRepository.findById(isbn).orElseThrow(RuntimeException::new)
                .getAvailableLibraries();
    }

    public List<Library> getAvailableLibraries(String isbn) {
        final String url = "http://localhost:8081/libraries/";
        List<Library> libraries = new ArrayList<>();
        List<Long>  libraryIds = bookRepository.findById(isbn).orElseThrow(RuntimeException::new)
                .getAvailableLibraries();
        for (Long id : libraryIds) {
            libraries.add(restTemplate.getForObject(url + id, Library.class));
        }
        return libraries;
    }

    public void borrowBook(String isbn, long libraryId) {
        Book book = bookRepository.findById(isbn).orElseThrow(RuntimeException::new);
        book.borrowFrom(libraryId);
        bookRepository.save(book);
        //System.out.println(book.getAvailableLibraries());
    }

    public void returnBook(String isbn, long libraryId) {
        Book book = bookRepository.findById(isbn).orElseThrow(RuntimeException::new);
        book.returnTo(libraryId);
        bookRepository.save(book);
        BookEvent event = new BookEvent();
        event.setEventName("return");
        event.setBookIsbn(book.getIsbn());
        event.setBookTitle(book.getTitle());
        event.setLibraryId(libraryId);
        applicationEventPublisher.publishEvent(event);
    }

}
