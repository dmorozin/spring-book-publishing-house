package com.bookpublishinghouse.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String isbn;

    private String genre;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "author_book",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    @BatchSize(size = 25)
    private Set<Author> authors = new HashSet<>();

    public Book() {

    }

    public Book(String title, String isbn, String genre, Set<Author> authors) {

        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.authors = authors;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getIsbn() {

        return isbn;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public Set<Author> getAuthors() {

        return authors;
    }

    public void setAuthors(Set<Author> authors) {

        this.authors = authors;
    }

    public void removeAuthor(Author author) {

        this.authors.remove(author);
    }
}
