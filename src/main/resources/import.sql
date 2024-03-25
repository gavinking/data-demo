insert into Publisher (id, name) values (1, 'Manning');
insert into Book (isbn, title, text, type, publisher_id, pages) values ('9781932394153', 'Hibernate in Action', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', 'Book', 1, 100);
insert into Book (isbn, title, text, type, publisher_id, pages) values ('9781932394887', 'Java Persistence with Hibernate', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit...', 'Book', 1, 200);
insert into Author (ssn, name) values ('126-24-9867', 'Gavin King');
insert into Author (ssn, name) values ('XXX-YYY', 'Christian Bauer');
insert into Author_Book (authors_ssn, books_isbn) values ('126-24-9867', '9781932394153');
insert into Author_Book (authors_ssn, books_isbn) values ('126-24-9867', '9781932394887');
insert into Author_Book (authors_ssn, books_isbn) values ('XXX-YYY', '9781932394153');
insert into Author_Book (authors_ssn, books_isbn) values ('XXX-YYY', '9781932394887');