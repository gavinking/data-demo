package org.example;

import jakarta.data.Order;
import jakarta.data.repository.By;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Delete;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import org.hibernate.StatelessSession;
import org.hibernate.annotations.processing.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface Bookshop extends CrudRepository<Book,String> {
    @Find
    List<Book> byPublisher(String publisher_name);

    @Delete
    int deleteAll();

    @Find
    Stream<Author> allAuthors(Order<Author> order);

    @Delete
    int deleteByDate(LocalDate publicationDate);

    // "in" conditions via multivalued parameters
    // (this is a Hibernate extension)

    @Find
    List<Book> findByIdIn(@By("isbn") String[] ids);

    @Delete
    int deleteByIdIn(@By("isbn") String[] ids);

    // @Pattern is a Hibernate extension
    @Find
    List<Book> byTitle(@Pattern String title);

    @Query("select sum(quantitySold * price) from Book")
    BigDecimal totalSales();

    // session accessor method
    StatelessSession session();

    // user-implemented concrete method
    default List<Book> booksBy(String authorName) {
        return session()
                .createSelectionQuery("from Book b join b.authors a where a.name = :name", Book.class)
                .setParameter("name", authorName)
                .setCacheable(true)
                .setCacheRegion("books-by-author-name")
                .setComment("Books by Author name")
                .getResultList();
    }
}
