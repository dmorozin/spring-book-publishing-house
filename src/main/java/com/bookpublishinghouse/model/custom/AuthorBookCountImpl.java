package com.bookpublishinghouse.model.custom;

public class AuthorBookCountImpl implements AuthorBookCount {

    private String name;
    private int bookCount;

    public AuthorBookCountImpl(String name, int bookCount) {

        this.name = name;
        this.bookCount = bookCount;
    }

    public String getName() {

        return name;
    }

    public int getBookCount() {

        return bookCount;
    }
}
