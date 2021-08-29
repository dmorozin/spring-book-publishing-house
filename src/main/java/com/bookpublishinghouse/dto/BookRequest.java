package com.bookpublishinghouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel(description = "Request data for publishing a new book")
public class BookRequest {

    @ApiModelProperty(value = "The unique title")
    private String title;

    @ApiModelProperty(value = "The book's genre")
    private String genre;

    @ApiModelProperty(value = "The unique isbn")
    private String isbn;

    @ApiModelProperty(value = "IDs of book's authors")
    private Set<Long> authorIds;

    public BookRequest() {

    }

    public BookRequest(String title, String genre, String isbn, Set<Long> authorIds) {

        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.authorIds = authorIds;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public String getIsbn() {

        return isbn;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public Set<Long> getAuthorIds() {

        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {

        this.authorIds = authorIds;
    }
}
