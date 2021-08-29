package com.bookpublishinghouse.dto;

import java.util.List;

public class BookDTOBuilder {

    private Long id;
    private String title;
    private String genre;
    private String isbn;
    private List<AuthorDTO> authors;
    private List<String> authorNames;

    public BookDTOBuilder() {

    }

    public BookDTO build() {

        return new BookDTO(id,
                           title,
                           genre,
                           isbn,
                           authors,
                           authorNames);
    }

    public BookDTOBuilder withId(Long id) {

        this.id = id;
        return this;
    }

    public BookDTOBuilder withTitle(String title) {

        this.title = title;
        return this;
    }

    public BookDTOBuilder withGenre(String genre) {

        this.genre = genre;
        return this;
    }

    public BookDTOBuilder withIsbn(String isbn) {

        this.isbn = isbn;
        return this;
    }

    public BookDTOBuilder withAuthors(List<AuthorDTO> authors) {

        this.authors = authors;
        return this;
    }

    public BookDTOBuilder withAuthorNames(List<String> authorNames) {

        this.authorNames = authorNames;
        return this;
    }
}
