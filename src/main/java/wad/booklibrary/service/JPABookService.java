/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.booklibrary.domain.Book;
import wad.booklibrary.domain.SearchType;
import static wad.booklibrary.domain.SearchType.AUTHOR;
import static wad.booklibrary.domain.SearchType.DESCRIPTION;
import static wad.booklibrary.domain.SearchType.ISBN;
import static wad.booklibrary.domain.SearchType.NONE;
import static wad.booklibrary.domain.SearchType.PUBLISHER;
import static wad.booklibrary.domain.SearchType.PUBLISHING_YEAR;
import wad.booklibrary.repository.BookRepository;

/**
 *
 * @author jiri
 */
@Service
@Transactional
public class JPABookService implements BookService{
    @Autowired
    BookRepository bookRepository;
    
    @PostConstruct
    private void init(){
        //populate the library with some books
        
        Book lotr = new Book();
        lotr.setTitle("The Lord of The Rings");
        lotr.setISBN("9780061917806");
        lotr.setPublishingYear("2009");
        lotr.setPublishers(Arrays.asList( new String[]{"HarperCollins"}));
        lotr.setAuthors(Arrays.asList(new String[]{"J.R.R. Tolkien"}));
        bookRepository.save(lotr);
        
        Book algo = new Book();
        algo.setTitle("Introduction to Algorithms");
        algo.setISBN("0262031418");
        algo.setAuthors(Arrays.asList(new String[]{"Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest"}));
        algo.setPublishers(Arrays.asList(new String[] {"MIT Press", "McGraw-Hill"}));
        algo.setPublishingYear("1990");
        bookRepository.save(algo);
        
        Book data = new Book();
        data.setTitle("Introduction to Data Mining");
        data.setISBN("0321420527");
        data.setAuthors(Arrays.asList(new String[]{"Pang-Ning Tan"}));
        data.setPublishers(Arrays.asList(new String[]{"ADDISON WESLEY PUBLI"}));
        data.setPublishingYear("2006");
        bookRepository.save(data);
        
        Book hobbit = new Book();
        hobbit.setTitle("The Hobbit, or, There and back again ");
        hobbit.setISBN("0345339681");
        hobbit.setAuthors(Arrays.asList(new String[]{"J.R.R Tolkien"}));
        hobbit.setPublishers(Arrays.asList(new String[]{"Ballantine Books"}));
        hobbit.setPublishingYear("1988");
        bookRepository.save(hobbit);
    }

    @Override
    public Page<Book> getBooks(int pageNumber, int pageSize) {
        return bookRepository.findByTitleNotNull(new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "title"));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public void updateBook(Long id, Book book) {
        if(bookRepository.exists(id)){
            book.setId(id);
            bookRepository.save(book);
        }
    }

    @Override
    public long getBookCount() {
        return bookRepository.count();
    }

    @Override
    public int getPageCount(int pageSize) {
        Long count = getBookCount();
        Double pageCount = Math.ceil(count.doubleValue() / pageSize );
        if(pageCount < 1){
            pageCount = 1.0;
        }
        return pageCount.intValue();
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Page<Book> getBooksBySearchCriteria(SearchType type, String searchArg, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "title");
        switch(type){
            case TITLE:
                return bookRepository.findByTitleContainingIgnoreCase(searchArg, pageRequest);
           case ISBN: 
                return bookRepository.findByISBNContainingIgnoreCase(searchArg, pageRequest);
            case PUBLISHING_YEAR:
                return  bookRepository.findByPublishingYearContainingIgnoreCase(searchArg, pageRequest);
            case DESCRIPTION:
                return bookRepository.findByDescriptionContainingIgnoreCase(searchArg, pageRequest);
            case AUTHOR:
                return bookRepository.findDistinctByAuthorsContainingIgnoreCase(searchArg, pageRequest);
            case PUBLISHER:
                return bookRepository.findDistinctByPublishersContainingIgnoreCase(searchArg, pageRequest);
            case NONE:
                return bookRepository.findByTitleNotNull(pageRequest);
            default:
                throw new IllegalArgumentException("Cannot search books by SearchType " + type.name());
        }
    }

    @Override
    public long getBookCount(SearchType type, String searchArg ) {
        switch(type){
            case TITLE:
                return bookRepository.countByTitleContainingIgnoreCase(searchArg);
            case ISBN: 
                return bookRepository.countByISBNContainingIgnoreCase(searchArg);
            case PUBLISHING_YEAR:
                return  bookRepository.countByPublishingYearContainingIgnoreCase(searchArg);
            case DESCRIPTION:
                return bookRepository.countByDescriptionContainingIgnoreCase(searchArg);
            case AUTHOR:
                return bookRepository.countDistinctByAuthorsContainingIgnoreCase(searchArg);
            case PUBLISHER:
                return bookRepository.countDistinctByPublishersContainingIgnoreCase(searchArg);
            case NONE:
                return bookRepository.count();
            default:
                throw new IllegalArgumentException("Cannot search books by SearchType " + type.name());
        }
    }

    @Override
    public int getPageCount(int pageSize, SearchType type, String searchArg) {
        Long count = getBookCount(type, searchArg);
        Double pageCount = Math.ceil(count.doubleValue() / pageSize );
        if(pageCount < 1){
            pageCount = 1.0;
        }
        return pageCount.intValue();
    }
    
}
