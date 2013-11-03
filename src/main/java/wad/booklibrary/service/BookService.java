/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import org.springframework.data.domain.Page;
import wad.booklibrary.domain.Book;
import wad.booklibrary.domain.SearchType;

/**
 *
 * @author jiri
 */
public interface BookService {

    public Page<Book> getBooks(int pageNumber, int pageSize);
    public Page<Book> getBooksBySearchCriteria(SearchType type, String searchArg, int pageNumber, int pageSize);
    
    public Book getBook(Long id);    
    public Book createBook(Book book);    
    public void deleteBook(Long id);
    public void updateBook(Long id, Book book);
    
    public long getBookCount();
    public long getBookCount(SearchType type, String searchArg);
    public int getPageCount(int pageSize);
    public int getPageCount(int pageSize, SearchType type, String searchArg);
    
}
