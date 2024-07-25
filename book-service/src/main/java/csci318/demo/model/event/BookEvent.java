package csci318.demo.model.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BookEvent {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String eventName;
    @Column
    private String bookIsbn;
    @Column
    private String bookTitle;
    @Column
    private Long libraryId;

    public BookEvent() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public String toString() {
        return "BookEvent{" +
                "event_name='" + eventName + '\'' +
                ", isbn='" + bookIsbn + '\'' +
                ", book_title='" + bookTitle + '\'' +
                ", library_id=" + libraryId +
                '}';
    }
}
