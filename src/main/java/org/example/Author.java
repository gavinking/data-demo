package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
public class Author {
    @Id
    String ssn;

    @NotNull
    String name;

    Address address;

    boolean deceased;

    @ManyToMany
    Set<Book> books;

    @Override
    public String toString() {
        return name + " [" + ssn + "]";
    }
}
