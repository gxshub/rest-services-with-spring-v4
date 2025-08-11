package csci318.demo.controller;

import csci318.demo.service.BookService;
import csci318.demo.service.dto.BookDTO;
import csci318.demo.service.dto.LibraryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    List<BookDTO> allBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{isbn}")
    BookDTO findBook(@PathVariable String isbn) {
        return bookService.getBook(isbn);
    }

    @GetMapping("/books/{isbn}/available")
    List<LibraryDTO> availableLibraries(@PathVariable String isbn) {
        return bookService.getAvailableLibraries(isbn);
    }

    @PutMapping("/books/borrow/{isbn}/{libraryId}")
    void borrow(@PathVariable String isbn, @PathVariable String libraryId) {
        bookService.borrowBook(isbn, Long.parseLong(libraryId));
    }

    @PutMapping("/books/return/{isbn}/{libraryId}")
    void return1(@PathVariable String isbn, @PathVariable String libraryId) {
        bookService.returnBook(isbn, Long.parseLong(libraryId));
    }
}