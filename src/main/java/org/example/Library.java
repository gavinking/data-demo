package org.example;

import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.CursoredPage;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.Delete;
import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.OrderBy;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import jakarta.data.repository.Save;
import jakarta.data.repository.Update;

import java.time.LocalDate;
import java.util.List;

import static org.example._Book.ISBN;
import static org.example._Book.TITLE;

@Repository
public interface Library {

	@Find
	Book book(String isbn);

	@Find
	Book book(String title, LocalDate publicationDate);

	@Find
	List<Book> publications(Type type, Sort<Book> sort);

	@Find
	@OrderBy(TITLE)
	List<Book> booksByPublisher(String publisher_name);

	@Query("where title like :titlePattern")
	@OrderBy(TITLE)
	List<Book> booksByTitle(String titlePattern);

	// not required by Jakarta Data
	record BookWithAuthor(Book book, Author author) {}
	@Query("select b, a from Book b join b.authors a order by b.isbn, a.ssn")
	List<BookWithAuthor> booksWithAuthors();

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

	@Find
	@OrderBy(ISBN)
	CursoredPage<Book> allBooks(PageRequest<Book> pageRequest);

	@Find
	@OrderBy("name")
	@OrderBy("address.city")
	List<Author> allAuthors(Order<Author> order);
}
