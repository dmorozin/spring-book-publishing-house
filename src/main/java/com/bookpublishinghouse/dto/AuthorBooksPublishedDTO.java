package com.bookpublishinghouse.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Author's name and the number of published books")
public class AuthorBooksPublishedDTO {

    private String name;
    private int publishedBooksCount;

    public AuthorBooksPublishedDTO(String name, int publishedBooksCount) {

        this.name = name;
        this.publishedBooksCount = publishedBooksCount;
    }

    public String getName() {

        return name;
    }

    public int getPublishedBooksCount() {

        return publishedBooksCount;
    }
}
