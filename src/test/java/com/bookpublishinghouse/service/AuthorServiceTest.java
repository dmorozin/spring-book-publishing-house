package com.bookpublishinghouse.service;

import com.bookpublishinghouse.dto.AuthorBooksPublishedDTO;
import com.bookpublishinghouse.dto.AuthorDTO;
import com.bookpublishinghouse.dto.AuthorRequest;
import com.bookpublishinghouse.dto.SortBy;
import com.bookpublishinghouse.model.Author;
import com.bookpublishinghouse.model.Book;
import com.bookpublishinghouse.model.custom.AuthorBookCount;
import com.bookpublishinghouse.model.custom.AuthorBookCountImpl;
import com.bookpublishinghouse.repository.AuthorRepository;
import com.bookpublishinghouse.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void whenAddAuthor_thenReturnNewAuthorDTO() {

        AuthorRequest authorRequest = new AuthorRequest("Test");

        Author author = getMockedAuthor();

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO authorDTO = authorService.addAuthor(authorRequest);
        assertThat(authorDTO.getName()).isSameAs(authorRequest.getName());
    }

    @Test
    void whenUpdateAuthor_thenReturnUpdatedAuthorDTO() {

        long id = 1L;
        AuthorRequest authorRequest = new AuthorRequest("Updated Author");

        Author mockAuthor = getMockedAuthor();


        when(authorRepository.findById(id)).thenReturn(Optional.of(mockAuthor));

        Author updatedAuthor = new Author("Updated Author");
        updatedAuthor.setId(1L);

        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);
        AuthorDTO authorDTO = authorService.updateAuthor(id, authorRequest);

        assertThat(authorDTO.getName()).isSameAs(authorRequest.getName());
    }

    @Test
    void whenDeleteAuthor_thenDeleteAuthorAndRelationToBooks() {

        long id = 1L;

        Author mockAuthor = getMockedAuthor();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");
        book.setGenre("Genre");
        book.setIsbn("1111-2222");
        book.setAuthors(new HashSet<>(Collections.singletonList(mockAuthor)));

        Set<Book> books = new HashSet<>(Collections.singletonList(book));
        mockAuthor.setBooks(books);

        when(authorRepository.findById(id)).thenReturn(Optional.of(mockAuthor));

        authorService.deleteAuthor(id);

        verify(authorRepository, times(1)).delete(mockAuthor);
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    void whenGetAuthorsOrderByNull_thenReturnAuthorsOrderByCreatedAt() {

        SortBy sortBy = null;
        List<AuthorBookCount> authorBookCounts = getAuthorBookCountList();

        when(authorRepository.getAuthorsOrderedByCreatedAt()).thenReturn(authorBookCounts);
        List<AuthorBooksPublishedDTO> authors = authorService.getAuthors(sortBy);

        assertEquals(authors.get(0).getName(), authorBookCounts.get(0).getName());
    }

    @Test
    void whenGetAuthorsOrderByBooksCount_thenReturnAuthorsOrderByBooksCount() {

        SortBy sortBy = SortBy.BOOKS_COUNT;
        List<AuthorBookCount> authorBookCounts = getAuthorBookCountList();

        when(authorRepository.getAuthorsOrderedByBooksCount()).thenReturn(authorBookCounts);
        List<AuthorBooksPublishedDTO> authors = authorService.getAuthors(sortBy);

        assertEquals(authors.get(0).getName(), authorBookCounts.get(0).getName());
    }

    private Author getMockedAuthor() {

        Author author = new Author("Test");
        author.setId(1L);
        return author;
    }

    private List<AuthorBookCount> getAuthorBookCountList() {

        return Arrays.asList(new AuthorBookCountImpl("Author 1", 1),
                             new AuthorBookCountImpl("Author 2", 2));
    }
}
