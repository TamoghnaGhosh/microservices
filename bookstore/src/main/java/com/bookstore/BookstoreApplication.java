package com.bookstore;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.service.BookService;
import com.bookstore.vo.Book;
import com.bookstore.vo.BookInput;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class BookstoreApplication {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/bookstore/recommended")
	public String readingList() {
		return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
	}

	@RequestMapping(value = "/bookstore/create", method = RequestMethod.POST)
	public String createBook(@RequestBody BookInput bi) {

		Book book = bookService.save(bi);

		return book.getId().toString();
	}

	@RequestMapping(value = "/bookstore/books", method = RequestMethod.GET)
	public List<Book> getBooksList() {
		return bookService.getBooksList();
	}

	@RequestMapping(value = "/bookstore/books/{book_id}", method = RequestMethod.DELETE)
	public boolean deleteBook(@PathVariable("book_id") String book_id) {
		return bookService.deleteBook(UUID.fromString(book_id));
	}

	@RequestMapping(value = "/bookstore/update", method = RequestMethod.POST)
	public boolean updateBook(@RequestBody BookInput bi) {

		return bookService.updateBook(bi);
	}

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
}
