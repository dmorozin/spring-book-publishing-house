package com.bookpublishinghouse.repository.specification;

import com.bookpublishinghouse.model.Author;
import com.bookpublishinghouse.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookSpecification implements Specification<Book> {

    private String title;
    private String isbn;
    private String genre;
    private String author;

    public BookSpecification(String title, String isbn, String genre, String author) {

        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(author)) {
            Join<Book, Author> authorsJoin = root.join("authors");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(authorsJoin.get("name")),
                                                "%" + author.toLowerCase() + "%"));
        } else {
            root.fetch("authors");
        }

        if (Objects.nonNull(title)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                                                "%" + title.toLowerCase() + "%"));
        }

        if (Objects.nonNull(isbn)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")),
                                                "%" + isbn.toLowerCase() + "%"));
        }

        if (Objects.nonNull(genre)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("genre")),
                                                "%" + genre.toLowerCase() + "%"));
        }

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt")));
        criteriaQuery.distinct(true);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
