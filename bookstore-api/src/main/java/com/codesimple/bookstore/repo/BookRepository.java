package com.codesimple.bookstore.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codesimple.bookstore.dto.BookQueryDslDTO;
import com.codesimple.bookstore.entity.Book;
//import com.codesimple.bookstore.service.BookQueryDslDTO;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom{

	List<Book> findAllByYearOfPublicationInAndBookType(Set<Integer> yop, String bookType);
	
	String rawQuery = "select * from book where year_of_publication IN :yop";
//	String rawQuery = "select * from book where year_of_publication IN ?1"; If we dont use @Param in following, then we can follow this

    @Query(nativeQuery = true, value = rawQuery)
    List<Book> findAllByYearOfPublicationIn(@Param("yop") Set<Integer> yop);

}
