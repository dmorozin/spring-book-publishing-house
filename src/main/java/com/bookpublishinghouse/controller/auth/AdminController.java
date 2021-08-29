package com.bookpublishinghouse.controller.auth;

import com.bookpublishinghouse.dto.AuthorDTO;
import com.bookpublishinghouse.dto.AuthorRequest;
import com.bookpublishinghouse.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/author")
public class AdminController {

    private final AuthorService authorService;

    @Autowired
    public AdminController(AuthorService authorService) {

        this.authorService = authorService;
    }

    @PostMapping
    @ApiOperation(value = "Add a new author",
            notes = "Adding is limited only to admin. " +
                    "Authorize using JWT Bearer token with scope=admin",
            response = AuthorDTO.class)
    public ResponseEntity<AuthorDTO> addNewAuthor(@ApiParam(value = "Request data for adding a new author")
                                                  @RequestBody AuthorRequest authorRequest) {

        return ResponseEntity.ok(authorService.addAuthor(authorRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an author",
            notes = "Update an author with given id. " +
                    "Updating is limited only to admin. " +
                    "Authorize using JWT Bearer token with scope=admin",
            response = AuthorDTO.class)
    public ResponseEntity<AuthorDTO> updateAuthor(@ApiParam(value = "ID of author to update")
                                                  @PathVariable long id,
                                                  @ApiParam(value = "Request data for updating the author")
                                                  @RequestBody AuthorRequest authorRequest) {

        return ResponseEntity.ok(authorService.updateAuthor(id, authorRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an author",
            notes = "Delete an author with given id. " +
                    "Deleting is limited only to admin. " +
                    "Authorize using JWT Bearer token with scope=admin")
    public ResponseEntity<Void> deleteAuthor(@ApiParam(value = "ID of author to delete")
                                             @PathVariable long id) {

        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
