package com.bookstore.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.bookstore.vo.Book;

@Repository

@RepositoryRestResource(exported = false)
public interface BookRepository extends CrudRepository<Book, Long>{

}
