package com.codesimple.bookstore.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.dto.BookDTO;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.service.BookService;

import com.codesimple.bookstore.util.JwtUtils;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    
    @RequestMapping(value = "/api/allbooks", method = RequestMethod.GET)
    public List<Book> getAllBooks(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
    	try {
			JwtUtils.verify(auth);
		} catch (Exception e) {
			throw new Exception();
		}
    	return bookService.getAllBooks();
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    public List<Book> getBooks(
            @RequestParam(value = "yearOfPublications", required = false) Set<Integer> yop,
            @RequestParam(value = "bookType", required = false) String bookType) {
        return bookService.getBooks(yop, bookType);
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.POST)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @RequestMapping(value = "/api/books/{id}", method = RequestMethod.GET)
    public BookDTO getBookById(
            @PathVariable("id") Long bookId,
            @RequestParam(value = "authorData", required = false) boolean authorData
    ){
        return bookService.getBookById(bookId, authorData);
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.PUT)
    public Book updateBook(@RequestBody Book incomingBook) {
        return bookService.updateBook(incomingBook);
    }

    @RequestMapping(value = "/api/books/{bookId}", method = RequestMethod.DELETE)
    public String deleteBookById(@PathVariable Long bookId) {
        return bookService.deleteById(bookId);
    }

    @GetMapping("/api/raw/books")
    public APIResponse getBooksByRawQuery(@RequestParam(value = "yop") Set<Integer> yop){
        return bookService.getBooksByRawQuery(yop);
    }

    @GetMapping("/api/caughtException")
    public APIResponse getCaughtException(@RequestParam(value = "number") Integer yop){
        return bookService.getCaughtException(yop);
    }

    @GetMapping("/api/queryDsl/books")
    public APIResponse getBooksByQueryDsl(@RequestParam(value ="year") Integer year){
        return bookService.getBooksByQueryDsl(year);
    }


}
