package com.pluralsight.books;

import com.pluralsight.service.book.AuthorType;
import com.pluralsight.service.book.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookDao {
    static List<BookType> books = new ArrayList<>();

    static {
        AuthorType mulisch = AuthorBuilder.newBuilder()
                .addLastName("Mulisch").addFirstName("Harry").build();
        AuthorType abdolah = AuthorBuilder.newBuilder()
                .addLastName("Abdolah").addFirstName("Kader").build();

        BookType deAanslag = BookBuilder.newBuilder()
                .addEan13(1L).addTitle("De Aanslag").addAuthor(mulisch).build();
        BookType siegfried = BookBuilder.newBuilder()
                .addEan13(2L).addTitle("Siegfried").addAuthor(mulisch).build();
        BookType hetHuisVanDeMoskee = BookBuilder.newBuilder()
                .addEan13(3L).addTitle("Het huis van de moskee").addAuthor(abdolah).build();

        books.add(deAanslag);
        books.add(siegfried);
        books.add(hetHuisVanDeMoskee);
    }

    public List<BookType> getBooks() {
        return books;
    }
}
