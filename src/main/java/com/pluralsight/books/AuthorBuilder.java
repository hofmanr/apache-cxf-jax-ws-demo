package com.pluralsight.books;

import com.pluralsight.service.book.AuthorType;

public class AuthorBuilder {

    private String lastName;
    private String firstName;
    private String initials;

    public static AuthorBuilder newBuilder() {
        return new AuthorBuilder();
    }

    public AuthorBuilder addLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AuthorBuilder addFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AuthorBuilder addInitials(String initials) {
        this.initials = initials;
        return this;
    }

    private void validate() {
        if (this.lastName == null)
            throw new IllegalArgumentException("LastName is mandatory");
    }

    public AuthorType build() {
        validate();
        AuthorType author = new AuthorType();
        author.setLastName(this.lastName);
        author.setFirstName(this.firstName);
        author.setInitials(this.initials);
        return author;
    }
}
