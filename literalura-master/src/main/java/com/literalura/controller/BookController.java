package com.literalura.controller;

import com.literalura.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/search")
    public void searchBookByTitle(@RequestParam String title) {
        bookService.searchBookByTitle(title);
    }

    @GetMapping
    public List<Book> listAllBooks() {
        return bookService.listAllBooks();
    }

    @GetMapping("/language")
    public List<Book> listBooksByLanguage(@RequestParam String language) {
        return bookService.listBooksByLanguage(language);
    }
}
