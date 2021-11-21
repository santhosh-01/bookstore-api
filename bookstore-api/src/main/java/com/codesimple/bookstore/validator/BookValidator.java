package com.codesimple.bookstore.validator;

import com.codesimple.bookstore.entity.Book;
import org.springframework.stereotype.Component;
import com.codesimple.bookstore.common.Error;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookValidator {

    public List<Error> validateCreateBookRequest(Book book) {

        List<Error> errors = new ArrayList<>();

        // name
        if(book.getName() == null){
            Error error = new Error("name", "book name is null");
            errors.add(error);
        }

        // yop
        if(book.getYearOfPublication() == null){
            Error error = new Error("yop", "yop is null");
            errors.add(error);
        }

        // book type
        if(book.getBookType() == null){
            errors.add(new Error("bookType", "bookType is null"));
        }

        return errors;
    }
}