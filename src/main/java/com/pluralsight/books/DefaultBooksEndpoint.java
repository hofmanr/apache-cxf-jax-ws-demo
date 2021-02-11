package com.pluralsight.books;

import com.pluralsight.service.book.BookType;
import com.pluralsight.service.book.QueryType;
import com.pluralsight.service.books.Books;
import com.pluralsight.service.book.FindBookResult;

import javax.inject.Inject;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(portName="BooksSOAP", serviceName="Books",
        endpointInterface="com.pluralsight.service.books.Books",
        targetNamespace = "http://www.pluralsight.com/service/Books/")
public class DefaultBooksEndpoint implements Books {

    @Inject
    BookService bookService;

    @Override
    public FindBookResult findBooks(QueryType query) {
        List<BookType> books = new ArrayList<>();
        if (query.getEan13() != null)
            books = bookService.findBooksByEan13(query.getEan13());
        else if (query.getTitle() != null)
            books = bookService.findBooksByTitle(query.getTitle());
        else if (query.getAuthor() != null)
            books = bookService.findBooksByAuthor(query.getAuthor());
        FindBookResult result = new FindBookResult();
        result.getBooks().addAll(books);
        return result;
    }
}
