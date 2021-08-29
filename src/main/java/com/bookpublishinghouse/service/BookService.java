package com.bookpublishinghouse.service;

import com.bookpublishinghouse.dto.BookDTO;
import com.bookpublishinghouse.dto.BookRequest;

import java.util.List;

/**
 * Service responsible for fetching and adding book related data from repository.
 */
public interface BookService {

    /**
     * Saves book to database
     *
     * @param bookRequest Request containing book data.
     * @return Saved book
     */
    BookDTO addNewBook(BookRequest bookRequest);

    /**
     * Returns filtered list of books sorted by publishing time
     *
     * @param title  book title to search by
     * @param isbn   book isbn to search by
     * @param genre  book title to search by
     * @param author author name to search by
     * @return List of books with author names
     */
    List<BookDTO> getBooks(String title, String isbn, String genre, String author);
}
