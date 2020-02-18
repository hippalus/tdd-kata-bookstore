package com.bookstore.domain.exception.handler;

import com.bookstore.domain.exception.BookCouldNotFoundByBookStoreAndCategory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class DomainExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = BookCouldNotFoundByBookStoreAndCategory.class)
    public String handleBaseException(BookCouldNotFoundByBookStoreAndCategory e){
        return e.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e){return e.getMessage();}
}