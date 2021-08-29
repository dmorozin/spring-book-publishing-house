package com.bookpublishinghouse.controller;

import com.bookpublishinghouse.dto.AuthorBooksPublishedDTO;
import com.bookpublishinghouse.dto.SortBy;
import com.bookpublishinghouse.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/author")
public class PublicAuthorController {

    private final AuthorService authorService;

    @Autowired
    public PublicAuthorController(AuthorService authorService) {

        this.authorService = authorService;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve list of authors",
            notes = "Returns sorted list of authors containing author's name and the number of published books. " +
                    "Sorted by author's creation time by default. Can be sorted by the count of published books",
            response = AuthorBooksPublishedDTO.class)
    public ResponseEntity<List<AuthorBooksPublishedDTO>> getAuthors(@ApiParam(value = "Parameter to specify the sort type")
                                                                    @RequestParam(value = "sortBy", required = false) SortBy sortBy) {

        return ResponseEntity.ok(authorService.getAuthors(sortBy));
    }
}
