/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wad.booklibrary.domain.Book;

/**
 *
 * @author jiri
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleNotNull(Pageable pageable);
    
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Long countByTitleContainingIgnoreCase(String title);
    
    Page<Book> findByPublishingYearContainingIgnoreCase(String title, Pageable pageable);
    Long countByPublishingYearContainingIgnoreCase(String title);
    
    Page<Book> findByISBNContainingIgnoreCase(String title, Pageable pageable);
    Long countByISBNContainingIgnoreCase(String title);
    
    Page<Book> findByDescriptionContainingIgnoreCase(String title, Pageable pageable);
    Long countByDescriptionContainingIgnoreCase(String title);    
    
    Page<Book> findDistinctByAuthorsContainingIgnoreCase(String author, Pageable pageable);
    //NOT WORKING CORRECTLY!!! todo: write a query
    Long countDistinctByAuthorsContainingIgnoreCase(String author);
    
    Page<Book> findDistinctByPublishersContainingIgnoreCase(String author, Pageable pageable);
    //NOT WORKING CORRECTLY!!! todo: write a query
    Long countDistinctByPublishersContainingIgnoreCase(String author);
    
}
