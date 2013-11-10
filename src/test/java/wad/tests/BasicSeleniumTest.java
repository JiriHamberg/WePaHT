/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author jiri
 */
public class BasicSeleniumTest {
    private WebDriver driver;
    private String baseAddress;
    private String loginAddress;
    
    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
        this.baseAddress = "http://localhost:8090/app/";
        this.loginAddress = "http://localhost:8090/spring_security_login";
    }
    
    public void login() {
        String pass = "123";
        String user = "admin";
        WebElement userInput = driver.findElement(By.name("j_username"));
        userInput.sendKeys(user);
        WebElement passInput = driver.findElement(By.name("j_password"));
        passInput.sendKeys(pass);
        passInput.submit();
    }
    
    @Test
    public void testGetBooksContainsLordOfTheRings() {
        driver.get(baseAddress + "books");
        assertTrue(driver.getPageSource().contains("The Lord of The Rings"));
        assertTrue(driver.getPageSource().contains("The Hobbit, or, There and back again"));
        assertTrue(driver.getPageSource().contains("Introduction to Data Mining"));    
    }
    
    @Test
    public void testSearchByAuthorTolkien() {
        driver.get(baseAddress + "books");
        Select select = new Select(driver.findElement(By.id("searchType")));
        select.selectByVisibleText("Author");
        WebElement element = driver.findElement(By.id("searchInput"));
        element.sendKeys("tolkien");
        element.submit();
        assertTrue(driver.getPageSource().contains("The Lord of The Rings"));
        assertTrue(driver.getPageSource().contains("The Hobbit, or, There and back again"));
        assertFalse(driver.getPageSource().contains("Introduction to Data Mining"));        
    }
    
    @Test
    public void testSearchByTitle(){
        driver.get(baseAddress + "books");
        Select select = new Select(driver.findElement(By.id("searchType")));
        select.selectByVisibleText("Title");
        WebElement element = driver.findElement(By.id("searchInput"));
        element.sendKeys("introduction");
        element.submit();
        assertTrue(driver.getPageSource().contains("Introduction to Data Mining"));
        assertTrue(driver.getPageSource().contains("Introduction to Algorithms"));
    }
    
    @Test
    public void addBook() {
        driver.get(baseAddress + "add");
        login();
        String title = "A New Title";
        String isbn = "1948395791834";
        WebElement titleInput = driver.findElement(By.id("title"));
        titleInput.sendKeys(title);
        WebElement isbnInput = driver.findElement(By.id("isbn"));
        isbnInput.sendKeys(isbn);
        titleInput.submit();
        assertTrue(driver.getCurrentUrl().contains("view"));
        assertTrue(driver.getPageSource().contains(title));
        assertTrue(driver.getPageSource().contains(isbn));
    }
    
    @Test
    public void testBookNotFoundAfterDeleting() {
        driver.get(loginAddress);
        login();
        driver.get(baseAddress + "add");
        //add new book
        String title = "Aaaaaaaa";
        WebElement titleInput = driver.findElement(By.id("title"));
        titleInput.sendKeys(title);
        titleInput.submit();
        
        //search the newly created book
        Select select = new Select(driver.findElement(By.id("searchType")));
        select.selectByVisibleText("Title");
        WebElement element = driver.findElement(By.id("searchInput"));
        element.sendKeys(title);
        element.submit();
        
        //remove the newly created book
        driver.findElement(By.linkText("Edit")).click();        
        String editUrl = driver.getCurrentUrl();
        driver.findElement(By.id("deleteInput")).submit();
        driver.get(editUrl);
        //the url of the book should no longer be available
        assertTrue(driver.getPageSource().contains("HTTP ERROR 404"));
    }
    
}
