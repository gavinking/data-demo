package org.example;

import jakarta.data.Limit;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.CursoredPage;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.By;
import jakarta.data.repository.Delete;
import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.OrderBy;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import jakarta.data.repository.Save;
import jakarta.data.repository.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.query.SelectionQuery;

import java.time.LocalDate;
import java.util.List;

import static org.example._Book.TITLE;

// A repository interface which makes use of Jakarta Validation constraints
@Repository
public interface Library {

	@Find
	Book book(@NotNull String isbn);

	@Find
	Book book(@NotBlank String title, @NotNull LocalDate publicationDate);

	// a repository method returning SelectionQuery for control over query execution
	@Find
	SelectionQuery<Book> book2(@NotBlank String title, @NotNull LocalDate publicationDate);

	@Find
	List<Book> publications(@NotNull Type type, Sort<Book> sort);

	@Find
	@OrderBy(TITLE)
	List<Book> booksByPublisher(@NotBlank String publisher_name);

	@Query("where title like ?1")
	@OrderBy(TITLE)
	List<Book> booksByTitle(@NotBlank String titlePattern);

	@Query("update Author set address = :address where ssn = :id")
	boolean updateAuthorAddress(String id, Address address);

	// not required by Jakarta Data
	record BookWithAuthor(Book book, Author author) {}
	@Query("select b, a from Book b join b.authors a order by b.isbn, a.ssn")
	List<BookWithAuthor> booksWithAuthors();

	// a record type representing a projection (query select list)
	record AuthorBookSummary(String isbn, String ssn, String authorName, String title) {}

	// a repository method which returns a record type
	@Query("select isbn, ssn, name, title " +
			"from Author join books " +
			"where title like ?1")
	List<AuthorBookSummary> summariesForTitle(@NotBlank String pattern);

	@Query("delete from Book " +
			"where extract(year from publicationDate) < :year")
	int deleteOldBooks(int year);

	@Insert
	void create(Book book);

	@Update
	void update(Book book);

	@Delete
	void delete(Book book);

	@Update
	void update(Book[] books);

	@Save
	void upsert(Book book);

	@Find
	Author author(String ssn);

	@Insert
	void create(Author author);

	@Update
	void update(Author author);

	@Save
	void save(Publisher publisher);

	@Delete
	void delete(Publisher publisher);

	// pagination
	@Find
	Page<Author> allAuthors(PageRequest pageRequest, Sort<Author> sort);

	// key-based pagination
	@Find
	CursoredPage<Book> allBooks(PageRequest pageRequest, Order<Book> sort);

	// (static + dynamic) sorting and limiting
	@Find
	@OrderBy("name")
	@OrderBy("address.city")
	List<Author> allAuthors(Order<Author> order, Limit limit);

	// example queries by fields of embeddables

	// use of the @By annotation
	@Find
	List<Author> authorsByCity(@By("address.city") String city);

	// alternative to using @By
	@Find
	List<Author> authorsByCityAndPostcode(String address_city, String address_postcode);
}
