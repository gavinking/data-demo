package org.example;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    String isbn;

    @NaturalId
    String title;

    @NaturalId(mutable = true)
    LocalDate publicationDate;

    String text;

    @Enumerated(STRING)
    @Basic(optional = false)
    Type type = Type.Book;

    @ManyToOne(optional = false, fetch = LAZY)
    Publisher publisher;

    @ManyToMany(mappedBy = Author_.BOOKS)
    Set<Author> authors;

    @Basic(optional = false)
    int pages;

    BigDecimal price;
    BigInteger quantitySold;

    public Book(String isbn, String title, String text) {
        this.isbn = isbn;
        this.title = title;
        this.text = text;
    }

    protected Book() {}

    @Override
    public String toString() {
        return isbn + " : " + title + " [" + type + "]";
    }
}

