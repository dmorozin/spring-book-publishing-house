package com.bookpublishinghouse.service;

import com.bookpublishinghouse.dto.BookDTO;
import com.bookpublishinghouse.dto.BookRequest;
import com.bookpublishinghouse.exception.ResourceNotFoundException;
import com.bookpublishinghouse.model.Author;
import com.bookpublishinghouse.model.Book;
import com.bookpublishinghouse.utils.Constants;
import com.bookpublishinghouse.repository.AuthorRepository;
import com.bookpublishinghouse.repository.BookRepository;
import com.bookpublishinghouse.repository.specification.BookSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void whenAddNewBook_thenReturnBookDTO() {

        BookRequest bookRequest = new BookRequest("Book 1",
                                                  "Genre",
                                                  "1111-2222",
                                                  new HashSet<>(Arrays.asList(1L, 2L)));
        Set<Author> authors = getAuthors();

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("Book 1");
        savedBook.setGenre("Genre");
        savedBook.setIsbn("1111-2222");
        savedBook.setAuthors(authors);

        when(authorRepository.findAllByIdIn(anyCollection())).thenReturn(authors);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookDTO bookDTO = bookService.addNewBook(bookRequest);

        assertThat(bookDTO.getTitle()).isSameAs(bookRequest.getTitle());
    }

    @Test
    void whenAddNewBook_thenThrowResourceNotFoundException() {

        BookRequest bookRequest = new BookRequest("Book 1",
                                                  "Genre",
                                                  "1111-2222",
                                                  Collections.singleton(3L));
        Set<Author> authors = getAuthors();

        when(authorRepository.findAllByIdIn(anyCollection())).thenReturn(authors);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> bookService.addNewBook(bookRequest));
        String expectedMessage = exception.getMessage();
        assertEquals(expectedMessage, String.format(Constants.AUTHORS_NOT_FOUND,
                                                    bookRequest.getAuthorIds().toString()));
    }

    @Test
    void whenGetBooks_thenReturnBookDTOList() {

        List<Book> books = getBooks();

        when(bookRepository.findAll(any(BookSpecification.class))).thenReturn(books);
        List<BookDTO> bookDTOs = bookService.getBooks("", "", "", "");

        assertEquals(books.get(0).getTitle(), bookDTOs.get(0).getTitle());
    }

    private List<Book> getBooks() {

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Title 1");
        book1.setGenre("Genre");
        book1.setIsbn("1111-1111");
        book1.setAuthors(new HashSet<>(Arrays.asList(author1, author2)));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Title 2");
        book2.setGenre("Genre");
        book2.setIsbn("2222-2222");
        book2.setAuthors(Collections.singleton(author2));

        author1.setBooks(Collections.singleton(book1));
        author2.setBooks(new HashSet<>(Arrays.asList(book1, book2)));

        return Arrays.asList(book1, book2);
    }

    private Set<Author> getAuthors() {

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");

        return new HashSet<>(Arrays.asList(author1, author2));
    }
}
