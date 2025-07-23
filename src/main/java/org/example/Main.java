package org.example;

import jakarta.data.Limit;
import jakarta.data.Order;
import jakarta.data.page.PageRequest;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

import java.time.LocalDate;

import static jakarta.data.Order.by;
import static java.lang.System.out;

public class Main {
	public static void main(String[] args) {
		var config =
				new HibernatePersistenceConfiguration("Jakarta Data Example")
						.managedClasses(Book.class, Author.class, Publisher.class);

		try (var sessionFactory = config.createEntityManagerFactory()) {
			// export the schema and test data
			var schemaManager = sessionFactory.getSchemaManager();
			schemaManager.drop(true);
			schemaManager.create(true);

			sessionFactory.inStatelessSession(session -> {
				// repository is usually injected via CDI,
				// but here we just instantiate it
				final Library library = new Library_(session);

				// load a Book
				var book = library.book("9781932394153");
				out.println(book);
				// update the book
				LocalDate publicationDate = LocalDate.of(2005, 1, 1);
				book.publicationDate = publicationDate;
				library.update(book);
				// read it back by its natural id
				book = library.book("Hibernate in Action", publicationDate);
				out.println(book);

				// run some more @Find-style queries
				library.publications(Type.Book, _Book.title.asc())
						.forEach(publication -> out.println(publication.title));
				library.allAuthors(by(_Author.ssn.descIgnoreCase()),
								Limit.of(100))
						.forEach(author -> out.println(author.name));

				// a simple JDQL query
				library.booksByTitle("%Hibernate%")
						.forEach(b -> out.println(b.isbn));

				// run a more complicated JDQL query
				library.booksWithAuthors()
						.forEach(result -> out.println(result.book().isbn + ": "
								+ result.book().title + " by " + result.author().name));

				// create a Publisher
				var publisher = new Publisher();
				publisher.id = 100;
				publisher.name = "Cambridge University Press";
				// upsert it
				library.save(publisher);
				// delete it
				library.delete(publisher);

				// run an update query
				session.getTransaction().begin();
				library.updateAuthorAddress("126-24-9867",
						new Address("Peachtree Rd", "Atlanta", "3600"));
				session.getTransaction().commit();

				// run a query that uses "cursor-based" pagination
				var page = library.allBooks(PageRequest.ofSize(10),
						by(_Book.title.ascIgnoreCase(), _Book.isbn.asc()));
				out.println(page.totalElements());
				out.println(page.content());
				while (page.hasNext()) {
					page = library.allBooks(page.nextPageRequest().withoutTotal(),
							by(_Book.title.ascIgnoreCase(), _Book.isbn.asc()));
					out.println(page.content());
				}

				// here we see a repository that inherits from
				// a built-in supertype defined by the Data spec
				final Bookshop bookshop = new Bookshop_(session);
				bookshop.byPublisher("Manning")
						.forEach(b -> out.println(b.isbn));
				bookshop.findAll()
						.forEach(b -> out.println(b.publicationDate));

				// call a user-implemented concrete method
				bookshop.booksBy("Gavin King")
						.forEach(out::println);

				// sort by a field of an embeddable
				bookshop.allAuthors(Order.by(_Author.address_city.descIgnoreCase()))
						.forEach(author -> out.println(author.name));

				// run an analytic query
				out.println( "Total sales: " +  bookshop.totalSales() );
			});
		}
	}
}