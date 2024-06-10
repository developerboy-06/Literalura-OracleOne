package com.literalura.service;

import com.literalura.model.Author;
import com.literalura.model.Book;
import com.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    private final String API_URL = "https://gutendex.com/books?search=";

    public void searchBookByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + title;

        BookDTO[] books = restTemplate.getForObject(url, BookDTO[].class);

        if (books != null) {
            for (BookDTO bookDto : books) {
                Book book = convertToEntity(bookDto);
                bookRepository.save(book);
            }
        }
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    private Book convertToEntity(BookDTO bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setLanguage(bookDto.getLanguage());
        book.setDownloads(bookDto.getDownloads());

        List<Author> authors = bookDto.getAuthors().stream()
                .map(authorService::findOrCreateAuthor)
                .collect(Collectors.toList());

        book.setAuthors(authors);
        return book;
    }
}
