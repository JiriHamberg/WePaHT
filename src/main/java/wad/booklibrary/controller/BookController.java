/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.booklibrary.domain.Book;
import wad.booklibrary.domain.BookForm;
import wad.booklibrary.domain.SearchType;
import wad.booklibrary.service.BookService;
import wad.booklibrary.service.RemoteBookService;

/**
 *
 * @author jiri
 */
@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    RemoteBookService remoteBookService;
    
    private final Integer PAGE_SIZE = 3;
    
    @RequestMapping(value = "books", method = RequestMethod.GET)
    public String getBooksView(
        @RequestParam(value = "page", defaultValue = "1", required = false) String pageNumber,
        @RequestParam(value = "searchType", required = false) String searchType,
        @RequestParam(value = "searchArg", required = false) String searchArg,
        Model model 
   ){   
        SearchType type = SearchType.NONE;
        
        for(SearchType s : SearchType.values()){
            if(s.name().equalsIgnoreCase(searchType)){
                type = s;
            }
        }
        
        int pageCount = bookService.getPageCount(PAGE_SIZE, type, searchArg);
        int pageNum = 1;
        try {         
            pageNum = Integer.parseInt(pageNumber);
            if(pageNum < 1 ){
                pageNum = 1;
            } else if(pageNum > pageCount){
                pageNum = pageCount;
            }
        } catch (Throwable t){}
        
        model.addAttribute("page", pageNum);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchArg", searchArg);
        //first page index is 0
        model.addAttribute("books", bookService.getBooksBySearchCriteria(type, searchArg, pageNum - 1, PAGE_SIZE));
        return "list";
    }
    
    @RequestMapping(value = "books/{id}", method = RequestMethod.GET)
    public String getEditView(
            @ModelAttribute("bookForm") BookForm bookForm,
            @PathVariable("id") Long bookId,
            Model model) {
        
        model.addAttribute("bookId", bookId);
        model.addAttribute("submitText", "Update");
        bookForm.populate(bookService.getBook(bookId));
        return "bookedit";
    }
    
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String getBookView(
            @PathVariable("id") Long bookId,
            Model model){
        model.addAttribute("book", bookService.getBook(bookId));
        return "bookview";
    }
    
    @RequestMapping(value= "books", method = RequestMethod.POST)
    public String addBook(
            @Valid @ModelAttribute("bookForm") BookForm bookForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
            ) {
        if(bindingResult.hasErrors()){       
            return "addview";
        }
        
        Book book = bookService.createBook(bookForm.createBook());
        redirectAttributes.addAttribute("id", book.getId());
        redirectAttributes.addFlashAttribute("message", "Created book " + book.getTitle());
        return "redirect:/app/view/{id}";
    }   
    
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String getAddView(@ModelAttribute("bookForm") BookForm bookForm){
        return "addview";
    } 
       
    @RequestMapping(value = "books/{id}", method = RequestMethod.PUT)
    public String updateBook(
            @Valid @ModelAttribute("bookForm") BookForm bookForm,
            BindingResult bindingResult,
            @PathVariable("id") Long bookId,
            RedirectAttributes redirectAttributes,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("submitText", "Update");
            model.addAttribute("bookId", bookId);
            return "bookedit"; 
        }
        Book b = bookForm.createBook();
        bookService.updateBook(bookId, b);
        redirectAttributes.addAttribute("id", bookId);
        redirectAttributes.addFlashAttribute("message", "Book " + b.getTitle() + " was edited successfully");
        return "redirect:/app/view/{id}"; 
    }
    
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "books/{id}", method = RequestMethod.DELETE)
    public String deleteBook(
            @PathVariable("id") Long bookId,
            RedirectAttributes redirectAttributes){
        String title = bookService.getBook(bookId).getTitle();
        bookService.deleteBook(bookId);
        redirectAttributes.addFlashAttribute("message", title + " deleted successfully");
        return "redirect:/app/books";
    }
    
    @RequestMapping(value = "remote/get/isbn", method = RequestMethod.GET)
    public String getRemoteBookByISBN(
            @RequestParam("isbn") String ISBN,
            Model model){
        Book book = remoteBookService.getBookByISBN(ISBN);
        BookForm bookform = new BookForm(book);
        model.addAttribute("bookForm", bookform);
        if(book.getISBN().isEmpty()){
            model.addAttribute("message", "Could not find book with ISBN " + ISBN);
        } else {
            model.addAttribute("message", "Book found!");
        }
        return "addview";
    }
    
    @RequestMapping(value = "remote/get/title", method = RequestMethod.GET)
    public String getRemoteBookByTitle(
            @RequestParam("title") String title,
            Model model){
        Book book = remoteBookService.getBookByTitle(title);
        BookForm bookform = new BookForm(book);
        model.addAttribute("bookForm", bookform);
        if(book.getISBN().isEmpty()){
            model.addAttribute("message", "Could not find book with title " + title);
        } else {
            model.addAttribute("message", "Book found!");
        }
        return "addview";
    }
    
    
}
