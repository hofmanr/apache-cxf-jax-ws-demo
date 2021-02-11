package com.pluralsight.books;

import com.pluralsight.service.book.AuthorType;
import com.pluralsight.service.book.BookType;

public class BookBuilder {

    private long ean13;
    private String title;
    private AuthorType author;

    public static BookBuilder newBuilder() {
        return new BookBuilder();
    }

    public BookBuilder addEan13(long ean13) {
        this.ean13 = ean13;
        return this;
    }

    public BookBuilder addTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder addAuthor(AuthorType author) {
        this.author = author;
        return this;
    }

    private void validate() {
        if (this.title == null)
            throw new IllegalArgumentException("Title is mandatory");
        if (this.author == null)
            throw new IllegalArgumentException("A book must have an author");
    }


    public BookType build() {
        validate();
        BookType book = new BookType();
        book.setEan13(this.ean13);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        return book;
    }


}
