package csci318.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    //@GeneratedValue
    private long id;

    @Column(nullable = false)
    private String location;

    // this one-to-one relationship
    // is bidirectional
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    // Hide the library field.
    // This prevents an infinite nesting references of library and address.
    private Library library;

    public Address() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

}