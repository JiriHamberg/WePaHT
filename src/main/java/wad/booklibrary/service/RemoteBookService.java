/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import wad.booklibrary.domain.Book;

/**
 *
 * @author jiri
 */
public interface RemoteBookService {    
   
    public Book getBookByTitle(String title);    
    public Book getBookByISBN(String ISBN);
}
