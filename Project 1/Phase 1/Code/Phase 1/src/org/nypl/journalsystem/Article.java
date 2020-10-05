package org.nypl.journalsystem;

import java.util.List;

public class Article {
    String title;
    List<Author> authors;

    public Article(String title, List<Author> authors) {
        this.title = title;
        this.authors= authors;
    }

    public String toString() {
        return "title: " + title + ", Authors: " + authors;
    }
}
