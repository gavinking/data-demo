package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue
    long id;

    @NotNull
    String name;

    @OneToMany(mappedBy = Book_.PUBLISHER)
    Set<Book> books;

    @Override
    public String toString() {
        return name;
    }
}
