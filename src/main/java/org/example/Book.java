package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.Length;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Book {
    @Id
    @Size(min=10, max = 13)
    String isbn;

    @NaturalId
    String title;

    @NaturalId(mutable = true)
    LocalDate publicationDate;

    @Column(length = Length.LONG)
    String text;

    @NotNull
    @Enumerated(STRING)
    Type type = Type.Book;

    @NotNull
    @ManyToOne(fetch = LAZY)
    Publisher publisher;

    @ManyToMany(mappedBy = Author_.BOOKS)
    Set<Author> authors;

    int pages;

    BigDecimal price;
    BigInteger quantitySold;

    public Book(String isbn, String title, String text, Publisher publisher) {
        this.isbn = isbn;
        this.title = title;
        this.text = text;
        this.publisher = publisher;
    }

    protected Book() {}

    @Override
    public String toString() {
        return isbn + " : " + title + " [" + type + "]";
    }
}

