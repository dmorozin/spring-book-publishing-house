package com.bookpublishinghouse.service;

import com.bookpublishinghouse.dto.AuthorBooksPublishedDTO;
import com.bookpublishinghouse.dto.AuthorDTO;
import com.bookpublishinghouse.dto.AuthorRequest;
import com.bookpublishinghouse.dto.SortBy;
import com.bookpublishinghouse.exception.ResourceNotFoundException;
import com.bookpublishinghouse.model.Author;
import com.bookpublishinghouse.model.custom.AuthorBookCount;
import com.bookpublishinghouse.repository.AuthorRepository;
import com.bookpublishinghouse.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bookpublishinghouse.utils.Constants.AUTHOR_NOT_FOUND;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public AuthorDTO addAuthor(AuthorRequest authorRequest) {

        Author newAuthor = authorRepository.save(new Author(authorRequest.getName()));

        return new AuthorDTO(newAuthor.getId(), newAuthor.getName());
    }

    @Override
    public AuthorDTO updateAuthor(long id, AuthorRequest authorRequest) {

        Author author = authorRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException(String.format(AUTHOR_NOT_FOUND, id)));

        author.setName(authorRequest.getName());
        authorRepository.save(author);

        return new AuthorDTO(author.getId(), author.getName());
    }

    @Override
    public void deleteAuthor(long id) {

        Author author = authorRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException(String.format(AUTHOR_NOT_FOUND, id)));

        Set<Long> bookIds = new HashSet<>();

        author.getBooks().forEach(book -> {
            book.removeAuthor(author);
            if (book.getAuthors().isEmpty()) {
                bookIds.add(book.getId());
            }
        });

        authorRepository.delete(author);
        bookIds.forEach(bookRepository::deleteById);
    }

    @Override
    public List<AuthorBooksPublishedDTO> getAuthors(SortBy sortBy) {

        List<AuthorBookCount> authors = SortBy.BOOKS_COUNT.equals(sortBy) ?
                authorRepository.getAuthorsOrderedByBooksCount() : authorRepository.getAuthorsOrderedByCreatedAt();

        return authors.stream()
                      .map(author -> new AuthorBooksPublishedDTO(author.getName(), author.getBookCount()))
                      .collect(Collectors.toList());
    }
}
