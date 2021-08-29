package com.bookpublishinghouse.service;

import com.bookpublishinghouse.dto.AuthorBooksPublishedDTO;
import com.bookpublishinghouse.dto.AuthorDTO;
import com.bookpublishinghouse.dto.AuthorRequest;
import com.bookpublishinghouse.dto.SortBy;

import java.util.List;

/**
 * Service responsible for fetching, adding, updating and deleting author related data from repository.
 */
public interface AuthorService {

    /**
     * Saves author to database
     *
     * @param authorRequest Request containing name.
     * @return Saved author
     */
    AuthorDTO addAuthor(AuthorRequest authorRequest);

    /**
     * Updates author in database
     *
     * @param id            ID of author to update
     * @param authorRequest Request containing name.
     * @return Updated author
     */
    AuthorDTO updateAuthor(long id, AuthorRequest authorRequest);


    /**
     * Deletes author from database
     *
     * @param id ID of author to delete
     */
    void deleteAuthor(long id);

    /**
     * Returns list of authors
     *
     * @param sortBy enum that defines type of sort
     * @return List of authors with name and number of published books
     */
    List<AuthorBooksPublishedDTO> getAuthors(SortBy sortBy);
}
