package org.example;

import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.lang.System.out;

public class Main {
	public static void main(String[] args) {
		var config = new Configuration();
		List.of(Book.class, Author.class, Publisher.class)
				.forEach(config::addAnnotatedClass);

		try (var sessionFactory = config.buildSessionFactory()) {
			sessionFactory.inStatelessSession(session -> {
				final Library library = new Library_(session); // usually injected via CDI

				var book = library.book("9781932394153");
				out.println(book);
				book.publicationDate = LocalDate.of(2005, 1, 1);
				library.updateBook(book);

				library.booksWithAuthors()
						.forEach(result -> out.println(result.book().isbn + ": "
								+ result.book().title + " by " + result.author().name));
			});
		}
	}
}