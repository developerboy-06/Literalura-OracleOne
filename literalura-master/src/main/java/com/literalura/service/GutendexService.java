package com.literalura.service;

import com.literalura.model.Author;
import com.literalura.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GutendexService {

    private final String BASE_URL = "https://gutendex.com/books";

    @Autowired
    private RestTemplate restTemplate;

    public List<Book> searchBooksByTitle(String title) {
        String url = BASE_URL + "?search=" + title;
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
        return response != null ? mapToBooks(response.getResults()) : new ArrayList<>();
    }

    private List<Book> mapToBooks(List<GutendexBook> gutendexBooks) {
        List<Book> books = new ArrayList<>();
        for (GutendexBook gutendexBook : gutendexBooks) {
            Book book = new Book();
            book.setTitle(gutendexBook.getTitle());
            book.setLanguage(gutendexBook.getLanguages().get(0));  // Suponemos que siempre hay al menos un idioma
            book.setDownloads(gutendexBook.getDownloadCount());

            List<Author> authors = new ArrayList<>();
            for (GutendexAuthor gutendexAuthor : gutendexBook.getAuthors()) {
                Author author = new Author();
                author.setName(gutendexAuthor.getName());
                author.setBirthYear(gutendexAuthor.getBirthYear());
                author.setDeathYear(gutendexAuthor.getDeathYear());
                authors.add(author);
            }
            book.setAuthors(authors);
            books.add(book);
        }
        return books;
    }

    // Clases internas para mapear la respuesta de Gutendex
    private static class GutendexResponse {
        private List<GutendexBook> results;

        public List<GutendexBook> getResults() {
            return results;
        }

        public void setResults(List<GutendexBook> results) {
            this.results = results;
        }
    }

    private static class GutendexBook {
        private String title;
        private List<String> languages;
        private int downloadCount;
        private List<GutendexAuthor> authors;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public int getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(int downloadCount) {
            this.downloadCount = downloadCount;
        }

        public List<GutendexAuthor> getAuthors() {
            return authors;
        }

        public void setAuthors(List<GutendexAuthor> authors) {
            this.authors = authors;
        }
    }

    private static class GutendexAuthor {
        private String name;
        private int birthYear;
        private Integer deathYear;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(int birthYear) {
            this.birthYear = birthYear;
        }

        public Integer getDeathYear() {
            return deathYear;
        }

        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }
    }
}
