package com.pluralsight.books;

import com.pluralsight.service.book.BookType;

import java.util.List;

public interface BookService {

    List<BookType> findBooksByEan13(long ean13);

    List<BookType> findBooksByTitle(String title);

    List<BookType> findBooksByAuthor(String author);

}
