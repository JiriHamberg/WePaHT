/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.domain;

import java.util.Arrays;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author jiri
 */
public class BookForm {
    
    @NotBlank
    @Length(min = 3, max = 255)
    private String title;    
    
    @NumberFormat
    private String ISBN;
    
    private String authors;
    
    private String publishers;
    
    private String description;
    
    @NumberFormat
    private String publishingYear;

    public BookForm(){
        
    }
    
    public BookForm(Book book){
        this();
        this.populate(book);
    }
        
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }
    
      public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public Book createBook() {
        Book book = new Book();
        book.setTitle(title);
        book.setISBN(ISBN);
        book.setDescription(description);
        book.setPublishingYear(publishingYear);
        book.setAuthors(generateStringList(authors));
        book.setPublishers(generateStringList(publishers));        
        return book;
    } 
    
    public final void populate(Book book){
        title = book.getTitle();
        ISBN = book.getISBN();
        description = book.getDescription();
        publishingYear = book.getPublishingYear();
        authors = generateString(book.getAuthors());
        publishers = generateString(book.getPublishers());
    }
    
    private static List<String> generateStringList(String source){
        String[] array = source.split(",");
        for(int i=0; i<array.length; i++){
            if(array[i].length() > 0 && array[i].charAt(0) == ' '){
                array[i] = array[i].replaceAll("^\\s+", ""); //remove leading spaces
            }
        }
        return Arrays.asList(array);
    }
    
    private static String generateString(List<String> source) {
        String res = "";
        for(String s : source) {
            res += s +", ";
        }
        if(res.length() > 2){
            res = res.substring(0, res.length() - 2); // drop the last comma and space
        }
        return res; 
    }
   
}
