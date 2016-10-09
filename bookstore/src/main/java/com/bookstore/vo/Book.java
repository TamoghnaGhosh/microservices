package com.bookstore.vo;

import java.io.Serializable;
import java.util.UUID;
//import org.springframework.data.cassandra.mapping.PrimaryKey;
//import org.springframework.data.cassandra.mapping.Table;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="book")
public class Book implements Serializable{

	//@PrimaryKey
	//private UUID id;
	@Id
	@Column(name ="id")
	private Long id;
	@Column(name ="name")
	private String name;
	@Column(name ="author")
	private String author;
	
/*	public Book(Long id, String name, String author) {
		this.id = id;
		this.name = name;
		this.author = author;
	}
	public Book(String name, String author) {
		this.name = name;
		this.author = author;
	}*/	
	public Long getId() {
		return id;
	}
	public void setBookId(Long id) {
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