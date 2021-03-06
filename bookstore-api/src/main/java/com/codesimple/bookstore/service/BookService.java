package com.codesimple.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.common.BadRequestException;
import com.codesimple.bookstore.data.BookData;
import com.codesimple.bookstore.dto.AuthorDTO;
import com.codesimple.bookstore.dto.BookDTO;
import com.codesimple.bookstore.dto.BookQueryDslDTO;
import com.codesimple.bookstore.entity.Author;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.entity.BookAuthor;
import com.codesimple.bookstore.repo.BookAuthorRepository;
import com.codesimple.bookstore.repo.BookRepository;
import com.codesimple.bookstore.validator.BookValidator;
import com.codesimple.bookstore.common.Error;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookValidator bookValidator;

	@Autowired
	private BookAuthorRepository bookAuthorRepository;

	// Get
	public List<Book> getBooks(Set<Integer> yop, String bookType) {

		List<Book> bookList = new ArrayList<>();

		if (yop == null) {
			bookRepository.findAll().forEach(book -> bookList.add(book));
		} else {
			return bookRepository.findAllByYearOfPublicationInAndBookType(yop, bookType);
		}

		return bookList;
	}

	// Create
	public Book createBook(Book book) {

		// validation
		List<Error> errors = bookValidator.validateCreateBookRequest(book);

		// if not success
		if (errors.size() > 0) {
			throw new BadRequestException("bad request", errors);
		}

		// if success
		return bookRepository.save(book);
	}

	// Update
	public Book updateBook(Book incomingBook) {
		return bookRepository.save(incomingBook);
	}

	// Delete
	public String deleteById(Long bookId) {
		bookRepository.deleteById(bookId);

		return "Deleted Successfully";
	}

	// raw query - get books
	public APIResponse getBooksByRawQuery(Set<Integer> yop) {

		APIResponse apiResponse = new APIResponse();

		// db call
		List<Book> bookList = bookRepository.findAllByYearOfPublicationIn(yop);

		// set data
		BookData bookData = new BookData();
		bookData.setBooks(bookList);

		// set api response
		apiResponse.setData(bookData);

		return apiResponse;
	}

	// Single resource
	public BookDTO getBookById(Long bookId, boolean authorData) {

		Book book = null;

		List<BookAuthor> bookAuthors = null;

		Optional<Book> bookOptional = bookRepository.findById(bookId);

		if (bookOptional.isPresent()) {
			book = bookOptional.get();
		}

		if (authorData) {
			bookAuthors = bookAuthorRepository.findAllByBookId(bookId);
		}

		BookDTO bookDTO = new BookDTO();

		// set book details
		bookDTO.setId(book.getId());
		bookDTO.setName(book.getName());
		bookDTO.setDesc(book.getDesc());
		bookDTO.setYearOfPublication(book.getYearOfPublication());
		bookDTO.setBookType(book.getBookType());

		// get author details
		List<AuthorDTO> authorDTOList = new ArrayList<>();
		if (bookAuthors != null) {
			for (BookAuthor bookAuthor : bookAuthors) {
				Author author = bookAuthor.getAuthor();

				AuthorDTO authorDTO = new AuthorDTO();
				authorDTO.setId(author.getId());
				authorDTO.setName(author.getName());
				authorDTO.setGender(author.getGender());

				authorDTOList.add(authorDTO);
			}

			// set author details
			bookDTO.setAuthors(authorDTOList);
		}
		return bookDTO;
	}

	public APIResponse getCaughtException(Integer yop) {

		int result = 100 / yop;

		APIResponse response = new APIResponse();
		response.setData(result);

		return response;
	}
	
	public APIResponse getBooksByQueryDsl(Integer year) {
        APIResponse apiResponse = new APIResponse();

        // repo to get the result
         List<Book> bookList = bookRepository.getAllBooksByQuerDsl(year);

        List<BookQueryDslDTO> bookQueryDslDTOS = bookRepository.getAllBooksByQuerDslDto(year);

        apiResponse.setData(bookQueryDslDTOS);

        //return
        return apiResponse;
    }

	public List<Book> getAllBooks() {
		
		List<Book> bookList = new ArrayList<Book>();
		bookRepository.findAll().forEach(book -> bookList .add(book));
		return bookList;
		
		
	}
}
