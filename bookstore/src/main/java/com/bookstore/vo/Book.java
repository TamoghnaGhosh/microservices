package com.bookstore.vo;

import java.util.UUID;
//import org.springframework.data.cassandra.mapping.PrimaryKey;
//import org.springframework.data.cassandra.mapping.Table;

//@Table
public class Book {

	//@PrimaryKey
	private UUID id;
	private String name;
	private String author;
	
	public Book(UUID id, String name, String author) {
		this.id = id;
		this.name = name;
		this.author = author;
	}
	
	public UUID getId() {
		return id;
	}
	public void setBookId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "Book[id = " + id + ", name = " + name + ", author = " + author + "]";
	}
	
}