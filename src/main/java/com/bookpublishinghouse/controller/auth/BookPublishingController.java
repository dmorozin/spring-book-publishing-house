package com.bookpublishinghouse.controller.auth;

import com.bookpublishinghouse.dto.BookDTO;
import com.bookpublishinghouse.dto.BookRequest;
import com.bookpublishinghouse.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/book")
public class BookPublishingController {

    private final BookService bookService;

    @Autowired
    public BookPublishingController(BookService bookService) {

        this.bookService = bookService;
    }

    @PostMapping
    @ApiOperation(value = "Publish a book",
            notes = "Publishing is limited only to authors. Authorize using JWT Bearer token with scope=author",
            response = BookDTO.class)
    public ResponseEntity<BookDTO> publishBook(@ApiParam(value = "Request data for publishing a new book")
                                               @RequestBody BookRequest bookRequest) {

        return ResponseEntity.ok(bookService.addNewBook(bookRequest));
    }
}
