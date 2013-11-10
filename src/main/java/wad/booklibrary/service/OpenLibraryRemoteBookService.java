/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wad.booklibrary.domain.Book;

/**
 *
 * @author jiri
 */
@Service
public class OpenLibraryRemoteBookService implements RemoteBookService {

    final String URL_BASE = "http://openlibrary.org/";
    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        return new ISBNBookFinder().getBookByISBN(ISBN);
    }

    @Override
    public Book getBookByTitle(String title) {
        return new TitleBookFinder().getBookByTitle(title);
    }

    private class ISBNBookFinder {
        public Book getBookByISBN(String ISBN) {
            Book book = new Book();
            String URL = URL_BASE + "api/books?bibkeys=ISBN:" + ISBN + "&jscmd=data&format=json";
            String json = restTemplate.getForObject(URL, String.class);
            JsonElement root = new JsonParser().parse(json);
            Set<Entry<String, JsonElement>> entries = root.getAsJsonObject().entrySet();

            //no book found with given ISBN
            if (entries.isEmpty()) {
                return book;
            }
            //we know there is just one entry with a lovely dynamic name -_-'
            JsonObject data = entries.iterator().next().getValue().getAsJsonObject();

            book.setISBN(ISBN);
            book.setTitle(getTitle(data));
            book.setAuthors(getAuthors(data));
            book.setPublishers(getPublishers(data));
            book.setPublishingYear(getPublishingYear(data));
            
            return book;
        }

        private String getTitle(JsonObject data) {
            try {
                return data.get("title").getAsString();
            } catch(NullPointerException e){
                return "";
            }
        }

        private List<String> getAuthors(JsonObject data) {
            try {
                List<String> res = new ArrayList<String>();
                JsonArray authors = data.get("authors").getAsJsonArray();
                for (JsonElement author : authors) {
                    String name = author.getAsJsonObject().get("name").getAsString();
                    res.add(name);
                }
                return res;
            } catch(NullPointerException e){
                return new ArrayList<String>();
            }
        }

        private List<String> getPublishers(JsonObject data) {
            try {
                List<String> res = new ArrayList<String>();
                JsonArray publishers = data.get("publishers").getAsJsonArray();
                for (JsonElement publisher : publishers) {
                    String name = publisher.getAsJsonObject().get("name").getAsString();
                    res.add(name);
                }
                return res;
            } catch(NullPointerException e){
                return new ArrayList<String>();
            }
        }

        private String getPublishingYear(JsonObject data) {
            try {
                return data.get("publish_date").getAsString();
            } catch(NullPointerException e){
                return "";
            }
            
        }
    }

    private class TitleBookFinder {
        public Book getBookByTitle(String title) {
            Book book = new Book();
            String URL = URL_BASE + "search.json?title=" + title;
            String json = restTemplate.getForObject(URL, String.class);
            JsonElement root = new JsonParser().parse(json);

            if (root.getAsJsonObject().get("docs").getAsJsonArray().size() == 0){
                return book;
            }
            //we'll just pick the first search result
            JsonObject data = root.getAsJsonObject().get("docs").getAsJsonArray().get(0).getAsJsonObject();
            
            book.setISBN(getISBN(data));
            book.setTitle(getTitle(data));
            book.setAuthors(getAuthors(data));
            book.setPublishers(getPublishers(data));
            book.setPublishingYear(getPublishingYear(data));
            book.setISBN(getISBN(data));
            
            return book;
        }

        private String getISBN(JsonObject data) {
            try{
                return data.get("isbn").getAsJsonArray().get(0).getAsString();
            } catch(NullPointerException e){
                return "";
            }
        }

        private String getTitle(JsonObject data) {
            try{
                return data.get("title").getAsString();
            } catch(NullPointerException e){
                return "";
            }
        }

        private List<String> getAuthors(JsonObject data) {
            try {
                List<String> res = new ArrayList<String>();
                for (JsonElement authorName : data.get("author_name").getAsJsonArray()) {
                    res.add(authorName.getAsString());
                }
                return res;
            } catch(NullPointerException e) {
                return new ArrayList<String>();
            }
        }

        private List<String> getPublishers(JsonObject data) {
            try{
                List<String> res = new ArrayList<String>();
                //for some reason first document entries seem to list freakishly many publishers
                for (JsonElement publisherName : data.get("publisher").getAsJsonArray()) {
                    res.add(publisherName.getAsString());
                }
                if(res.size() > 3){
                    res = res.subList(0, 2);
                }
                return res;
            } catch(NullPointerException e) {
                return new ArrayList<String>();
            }
        }

        private String getPublishingYear(JsonObject data) {
            try{
                return data.get("publish_year").getAsJsonArray().get(0).getAsString();
            } catch(NullPointerException e) {
                return "";
            }
        }
    }
}
