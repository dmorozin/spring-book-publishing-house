package com.bookpublishinghouse.controller;

import com.bookpublishinghouse.dto.BookDTO;
import com.bookpublishinghouse.service.BookService;
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
@RequestMapping("api/book")
public class PublicBookController {

    private final BookService bookService;

    @Autowired
    public PublicBookController(BookService bookService) {

        this.bookService = bookService;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve list of books",
            notes = "Returns books sorted by publishing time. Books can be searched by parameters.",
            response = BookDTO.class)
    public ResponseEntity<List<BookDTO>> getBooks(@ApiParam(value = "Search by unique title")
                                                  @RequestParam(value = "title", required = false) String title,
                                                  @ApiParam(value = "Search by unique ISBN")
                                                  @RequestParam(value = "isbn", required = false) String isbn,
                                                  @ApiParam(value = "Search by genre")
                                                  @RequestParam(value = "genre", required = false) String genre,
                                                  @ApiParam(value = "Search by author's name")
                                                  @RequestParam(value = "author", required = false) String author) {

        return ResponseEntity.ok(bookService.getBooks(title, isbn, genre, author));
    }
}
