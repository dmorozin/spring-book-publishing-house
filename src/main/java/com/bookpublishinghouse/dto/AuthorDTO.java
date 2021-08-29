package com.bookpublishinghouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information about author")
public class AuthorDTO {

    @ApiModelProperty(value = "The unique ID of the author")
    private long id;

    @ApiModelProperty(value = "The unique author's name")
    private String name;

    public AuthorDTO(long id, String name) {

        this.id = id;
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public String getName() {

        return name;
    }
}
