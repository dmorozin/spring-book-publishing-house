package com.bookpublishinghouse.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public Author() {

    }

    public Author(String name) {

        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Set<Book> getBooks() {

        return books;
    }

    public void setBooks(Set<Book> books) {

        this.books = books;
    }
}
