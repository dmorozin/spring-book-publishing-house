package com.bookpublishinghouse.service;

import com.bookpublishinghouse.dto.AuthorDTO;
import com.bookpublishinghouse.dto.BookDTO;
import com.bookpublishinghouse.dto.BookDTOBuilder;
import com.bookpublishinghouse.dto.BookRequest;
import com.bookpublishinghouse.exception.ResourceNotFoundException;
import com.bookpublishinghouse.model.Author;
import com.bookpublishinghouse.model.Book;
import com.bookpublishinghouse.repository.AuthorRepository;
import com.bookpublishinghouse.repository.BookRepository;
import com.bookpublishinghouse.repository.specification.BookSpecification;
import com.bookpublishinghouse.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDTO addNewBook(BookRequest bookRequest) {

        Set<Author> authors = authorRepository.findAllByIdIn(bookRequest.getAuthorIds());

        if (!checkIfAuthorsExists(authors, bookRequest.getAuthorIds())) {
            throw new ResourceNotFoundException(String.format(Constants.AUTHORS_NOT_FOUND,
                                                              bookRequest.getAuthorIds().toString()));
        }

        Book book = new Book(bookRequest.getTitle(),
                             bookRequest.getIsbn(),
                             bookRequest.getGenre(),
                             authors);

        bookRepository.save(book);

        List<AuthorDTO> authorDTOS = authors.stream()
                                            .map(author -> new AuthorDTO(author.getId(), author.getName()))
                                            .collect(Collectors.toList());

        BookDTOBuilder bookDTOBuilder = new BookDTOBuilder().withId(book.getId())
                                                            .withTitle(book.getTitle())
                                                            .withGenre(book.getGenre())
                                                            .withIsbn(book.getIsbn())
                                                            .withAuthors(authorDTOS);

        return bookDTOBuilder.build();
    }

    @Override
    public List<BookDTO> getBooks(String title, String isbn, String genre, String author) {

        BookSpecification bookSpecification = new BookSpecification(title, isbn, genre, author);
        List<Book> books = bookRepository.findAll(bookSpecification);

        return books.stream()
                    .map(book -> new BookDTOBuilder().withTitle(book.getTitle())
                                                     .withGenre(book.getGenre())
                                                     .withIsbn(book.getIsbn())
                                                     .withAuthorNames(getAuthorNames(book))
                                                     .build())
                    .collect(Collectors.toList());
    }

    private List<String> getAuthorNames(Book book) {

        return book.getAuthors()
                   .stream()
                   .map(Author::getName)
                   .collect(Collectors.toList());
    }

    private boolean checkIfAuthorsExists(Set<Author> authors, Set<Long> authorIds) {

        if (authors.isEmpty()) {
            return false;
        }

        return authors.stream()
                      .map(Author::getId)
                      .collect(Collectors.toSet())
                      .containsAll(authorIds);
    }
}
