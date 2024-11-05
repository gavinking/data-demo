insert into Publisher (id, name) values (1, 'Manning');
insert into Book (isbn, title, text, type, publisher_id, pages, quantitySold, price) values ('9781932394153', 'Hibernate in Action', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', 'Book', 1, 100, 50000, 44.95);
insert into Book (isbn, title, text, type, publisher_id, pages, quantitySold, price) values ('9781932394887', 'Java Persistence with Hibernate', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', 'Book', 1, 200, 30000, 59.99);
insert into Author (ssn, name, deceased) values ('126-24-9867', 'Gavin King', false);
insert into Author (ssn, name, deceased) values ('XXX-YYY', 'Christian Bauer', false);
insert into Author_Book (authors_ssn, books_isbn) values ('126-24-9867', '9781932394153');
insert into Author_Book (authors_ssn, books_isbn) values ('126-24-9867', '9781932394887');
insert into Author_Book (authors_ssn, books_isbn) values ('XXX-YYY', '9781932394153');
insert into Author_Book (authors_ssn, books_isbn) values ('XXX-YYY', '9781932394887');