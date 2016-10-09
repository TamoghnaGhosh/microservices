package com.bookstore.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.config.BookMQRepository;
import com.bookstore.vo.Book;
import com.bookstore.vo.BookInput;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepo;


	@Autowired
	private BookMQRepository reportDataMQRepository;
	
	private static final Logger log = Logger.getLogger(BookService.class);
	// private CassandraOperations cassandraOperations;
/*	private static Cluster cluster;
	private static Session session;

	static {
		cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
		session = cluster.connect("bookstore");
	}*/

	public Book save(BookInput bi) {
		// CassandraTemplate cassandraOps = new CassandraTemplate(session);
		// Book book = cassandraOps.insert(new Book(UUIDs.timeBased(),
		// bi.getName(), bi.getAuthor()));
/*		session.execute("insert into book(id, name, author) values("
				+ book.getId() + ", '" + book.getName() + "', '"
				+ book.getAuthor() + "')");*/
		log.info("name: " + bi.getName());
		Book book = new Book();
		book.setBookId(Calendar.getInstance().getTimeInMillis());
		book.setName(bi.getName());
		book.setAuthor(bi.getAuthor());
		bookRepo.save(book);
		
		return book;
	}

	public boolean deleteBook(long id) {
		try {
			//session.execute("delete from book where id=" + uid);
			Book book = bookRepo.findOne(id);
			bookRepo.delete(book);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Book> getBooksList() {

		//ResultSet rs = session.execute("select * from book");

		List<Book> booksList = new ArrayList<Book>();
		for (Book book : bookRepo.findAll()) {
			booksList.add(book);
		}

		return booksList;
	}

	public boolean updateBook(BookInput bi) {
		try {
/*			session.execute("UPDATE book SET name = '" + book.getName()
					+ "', author = '" + book.getAuthor() + "' WHERE id = "
					+ book.getId() + "");
*/				
			Book book = new Book();
			book.setBookId(Long.valueOf(bi.getId()));
			book.setName(bi.getName());
			book.setAuthor(bi.getAuthor());
			bookRepo.save(book);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void sendData(Book b) {
		try {
			log.info("Posting message to rabbit MQ for the report id == "
					+ b.getId());
			reportDataMQRepository.sendMessage(b);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}	
}
