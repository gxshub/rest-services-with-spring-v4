package csci318.demo.service;

import csci318.demo.model.Book;
import csci318.demo.model.Library;
import csci318.demo.model.event.BookEvent;
import csci318.demo.repository.BookRepository;
import csci318.demo.service.dto.BookDTO;
import csci318.demo.service.dto.LibraryDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {


    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    BookService(BookRepository bookRepository, RestTemplate restTemplate,
                ApplicationEventPublisher applicationEventPublisher) {
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookDTO bookDto = new BookDTO();
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setTitle(book.getTitle());
                    return bookDto;
                }).collect(Collectors.toList());
    }

    public BookDTO getBook(String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(RuntimeException::new);
        BookDTO bookDto = new BookDTO();
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }

    public List<LibraryDTO> getAvailableLibraries(String isbn) {
        final String url = "http://localhost:8081/libraries/";
        List<Library> libraries = new ArrayList<>();
        List<Long> libraryIds = bookRepository.findById(isbn).orElseThrow(RuntimeException::new)
                .getAvailableLibraries();
        for (Long id : libraryIds) {
            libraries.add(restTemplate.getForObject(url + id, Library.class));
        }
        return libraries.stream()
                .map(lib -> {
                    LibraryDTO libDto = new LibraryDTO();
                    libDto.setLibraryName(lib.getName());
                    libDto.setPostcode(Long.toString(lib.getId()));
                    return libDto;
                }).collect(Collectors.toList());
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
