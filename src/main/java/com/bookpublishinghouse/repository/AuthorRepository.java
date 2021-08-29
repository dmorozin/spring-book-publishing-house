package com.bookpublishinghouse.repository;

import com.bookpublishinghouse.model.Author;
import com.bookpublishinghouse.model.custom.AuthorBookCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Set<Author> findAllByIdIn(Collection<Long> ids);

    @Query("SELECT a.name as name, count(b) as bookCount FROM Author a " +
            "left join a.books b " +
            "group by a.id " +
            "order by count(b) desc, a.createdAt desc, a.id desc")
    List<AuthorBookCount> getAuthorsOrderedByBooksCount();

    @Query("SELECT a.name as name, count(b) as bookCount FROM Author a " +
            "left join a.books b " +
            "group by a.id " +
            "order by a.createdAt desc, a.id desc")
    List<AuthorBookCount> getAuthorsOrderedByCreatedAt();
}
