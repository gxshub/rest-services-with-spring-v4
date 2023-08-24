package csci318.demo.controller;

import csci318.demo.controller.dto.BookDTO;
import csci318.demo.controller.dto.LibraryDTO;
import csci318.demo.model.Book;
import csci318.demo.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    List<BookDTO> allBooks() {

        return bookService.getAllBooks()
                .stream()
                .map(book -> {
                    BookDTO bookDto = new BookDTO();
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setTitle(book.getTitle());
                    return bookDto;
                }).collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    BookDTO findBook(@PathVariable String isbn){
        BookDTO bookDto = new BookDTO();
        Book book = bookService.getBook(isbn);
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }

    @GetMapping("/books/{isbn}/available")
    List<LibraryDTO> availableLibraries(@PathVariable String isbn) {

        //Functional style
        return  bookService.getAvailableLibraries(isbn)
                .stream()
                .map(lib -> {
                    LibraryDTO libDto = new LibraryDTO();
                    libDto.setLibraryName(lib.getName());
                    libDto.setPostcode(Long.toString(lib.getId()));
                    return libDto;
                }).collect(Collectors.toList());

        //Traditional style
        /*
        libDto.setLibraryName(lib.getName());
        List<LibraryDto> libraryDtos = new ArrayList<>();
        List<Library> libraries = bookService.getAvailableLibraries(isbn);
        for (Library library: libraries) {
            LibraryDto libDto = new LibraryDto();
            libDto.setLibraryName(library.getName());
            libDto.setPostcode(Long.toString(library.getId()));
            libraryDtos.add(libDto);
        }
        return libraryDtos;
         */
    }

    @PutMapping("/books/borrow/{isbn}/{libraryId}")
    void borrow(@PathVariable String isbn, @PathVariable String libraryId) {
        bookService.borrowBook(isbn,Long.parseLong(libraryId));
    }

    @PutMapping("/books/return/{isbn}/{libraryId}")
    void return1(@PathVariable String isbn, @PathVariable String libraryId) {
        bookService.returnBook(isbn,Long.parseLong(libraryId));
    }
}