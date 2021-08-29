package com.bookpublishinghouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Request data for adding a new author")
public class AuthorRequest {

    @ApiModelProperty(value = "The unique name")
    private String name;

    public AuthorRequest() {

    }

    public AuthorRequest(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
