package org.example;

import jakarta.data.repository.Delete;
import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import jakarta.data.repository.Save;
import jakarta.data.repository.Update;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Library {

	@Find
	Book book(String isbn);

	@Find
	Book books(String title, LocalDate publicationDate);

	@Find // should be publisher_name in Jakarta Data
	List<Book> booksByPublisher(String publisher$name);

	@Query("where title like :titlePattern")
	List<Book> booksByTitle(String titlePattern);

	// not required by Jakarta Data
	record BookWithAuthor(Book book, Author author) {}
	@Query("select b, a from Book b join b.authors a")
	List<BookWithAuthor> booksWithAuthors();

	@Insert
	void createBook(Book book);

	@Update
	void updateBook(Book book);

	@Delete
	void deleteBook(Book book);

	@Save
	void upsertBook(Book book);

	@Find
	Author author(String ssn);

	@Insert
	void createAuthor(Author author);

	@Update
	void updateAuthor(Author author);

}
