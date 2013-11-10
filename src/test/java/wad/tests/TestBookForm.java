/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tests;


import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import wad.booklibrary.domain.Book;
import wad.booklibrary.domain.BookForm;

/**
 *
 * @author jiri
 */
public class TestBookForm {
    
    private Book b = new Book();
    private BookForm bookForm;
    
    @Before
    public void init() {
        b.setTitle("The Lord of The Rings");
        b.setISBN("9780061917806");
        b.setPublishingYear("2009");
        b.setPublishers(Arrays.asList( new String[]{"Aaaa","Bbbb", "Cccc Dddd"}));
        b.setAuthors(Arrays.asList(new String[]{"J.R.R. Tolkien", "Some other guy"}));
    }
    
    @Test
    public void testConvertingToBookFormEqualsBook(){
        bookForm = new BookForm(b);
        assertEquals(bookForm.getAuthors(), "J.R.R. Tolkien, Some other guy");
        assertEquals(bookForm.getPublishers(), "Aaaa, Bbbb, Cccc Dddd");
        assertEquals(bookForm.getISBN(), b.getISBN());
        assertEquals(bookForm.getTitle(), b.getTitle());
        assertEquals(bookForm.getDescription(), b.getDescription());
        assertEquals(bookForm.getPublishingYear(), b.getPublishingYear());
    }
    
    @Test
    public void testConvertingBackToBookEqualsOriginal(){
        bookForm = new BookForm(b);
        Book b2 = bookForm.createBook();
        assertEquals(b2.getAuthors(), b.getAuthors());
        assertEquals(b2.getPublishers(), b.getPublishers());
        assertEquals(b2.getISBN(), b.getISBN());
        assertEquals(b2.getTitle(), b.getTitle());
        assertEquals(b2.getDescription(), b.getDescription());
        assertEquals(b2.getPublishingYear(), b.getPublishingYear());
    }
    
}
