package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bookstore.vo.Book;
import com.bookstore.vo.BookInput;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class BookService {

	// private CassandraOperations cassandraOperations;
	private static Cluster cluster;
	private static Session session;

	static {
		cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
		session = cluster.connect("bookstore");
	}

	public Book save(BookInput bi) {
		// CassandraTemplate cassandraOps = new CassandraTemplate(session);
		// Book book = cassandraOps.insert(new Book(UUIDs.timeBased(),
		// bi.getName(), bi.getAuthor()));
		Book book = new Book(UUIDs.timeBased(), bi.getName(), bi.getAuthor());
		session.execute("insert into book(id, name, author) values("
				+ book.getId() + ", '" + book.getName() + "', '"
				+ book.getAuthor() + "')");

		return book;
	}

	public boolean deleteBook(UUID uid) {
		try {
			session.execute("delete from book where id=" + uid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Book> getBooksList() {

		ResultSet rs = session.execute("select * from book");

		List<Book> booksList = new ArrayList<Book>();
		for (Row row : rs.all()) {
			booksList.add(new Book(row.getUUID("id"), row.getString("name"),
					row.getString("author")));
		}

		return booksList;
	}

	public boolean updateBook(BookInput book) {
		try {
			session.execute("UPDATE book SET name = '" + book.getName()
					+ "', author = '" + book.getAuthor() + "' WHERE id = "
					+ book.getId() + "");
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

}
