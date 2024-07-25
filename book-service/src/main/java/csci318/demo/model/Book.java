package csci318.demo.model;

import csci318.demo.model.event.BookEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book extends AbstractAggregateRoot<Book> {

    @Id
    private String isbn;

    @Column
    private String title;

    @Column
    @ElementCollection(fetch = FetchType.EAGER, targetClass=Long.class)
    private List<Long> availableLibraries = new ArrayList<>();

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAvailableLibraries() {
        return availableLibraries;
    }

    public void addAvailableLibrary(Long libraryId) {
        this.availableLibraries.add(libraryId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", availableLibraries=" + availableLibraries.toString() +
                '}';
    }

    public boolean checkAvailability(Long libraryId) {
        return this.availableLibraries.contains(libraryId);
    }

    public void borrowFrom(long libraryId) {
        if (this.checkAvailability(libraryId)) {
            this.availableLibraries.remove(libraryId);
            BookEvent event = new BookEvent();
            event.setEventName("borrow");
            event.setBookIsbn(this.getIsbn());
            event.setBookTitle(this.getTitle());
            event.setLibraryId(libraryId);
            /**
             * Method to register the event
             * @param event
             */
            registerEvent(event);
        }
    }

    public void returnTo(long libraryId) {
        this.availableLibraries.add(libraryId);
    }
}
