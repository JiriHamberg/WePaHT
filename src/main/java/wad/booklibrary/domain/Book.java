/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jiri
 */
@Entity
@Table
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    
    @ElementCollection(targetClass = java.lang.String.class)
    @CollectionTable(name ="authors")
    private List<String> authors = new ArrayList<String>();
    
    @ElementCollection(targetClass = java.lang.String.class)
    private List<String> publishers = new ArrayList<String>();
    
    @Column
    private String publishingYear = "";
    
    @Column()
    private String iSBN = "";
    
    @Column
    //@NotBlank
    //@Length(max = 255)
    private String title = "";
    
    @Column
    private String description ="";

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getISBN() {
        return iSBN;
    }

    public void setISBN(String ISBN) {
        this.iSBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
