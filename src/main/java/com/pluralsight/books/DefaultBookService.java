package com.pluralsight.books;

import com.pluralsight.service.book.BookType;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultBookService implements BookService{

    @Inject
    BookDao bookDao;

    @Override
    public List<BookType> findBooksByEan13(final long ean13) {
        return bookDao.getBooks().stream()
                .filter(b -> ean13 == b.getEan13())
                .collect(Collectors.toList());
    }

    @Override
    public List<BookType> findBooksByTitle(final String title) {
        return bookDao.getBooks().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookType> findBooksByAuthor(String author) {
        return bookDao.getBooks().stream()
                .filter(b -> b.getAuthor().getLastName().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }
}
