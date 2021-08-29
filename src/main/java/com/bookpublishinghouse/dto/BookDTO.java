package com.bookpublishinghouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Information about a book")
public class BookDTO {

    @ApiModelProperty(value = "The unique ID of the book")
    private Long id;

    @ApiModelProperty(value = "The unique title")
    private String title;

    @ApiModelProperty(value = "The book's genre. Multiple books can have the same genre")
    private String genre;

    @ApiModelProperty(value = "The unique isbn")
    private String isbn;

    @ApiModelProperty(value = "List of authors containing ID and name. Returned when new book is published")
    private List<AuthorDTO> authors;

    @ApiModelProperty(value = "List of author names. Returned in the list of books")
    private List<String> authorNames;

    public BookDTO(Long id, String title, String genre, String isbn, List<AuthorDTO> authors, List<String> authorNames) {

        this.id = id;
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.authors = authors;
        this.authorNames = authorNames;
    }

    public Long getId() {

        return id;
    }

    public String getTitle() {

        return title;
    }

    public String getGenre() {

        return genre;
    }

    public String getIsbn() {

        return isbn;
    }

    public List<AuthorDTO> getAuthors() {

        return authors;
    }

    public List<String> getAuthorNames() {

        return authorNames;
    }
}
