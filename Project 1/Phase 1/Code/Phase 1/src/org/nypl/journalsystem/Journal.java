package org.nypl.journalsystem;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    Boolean fullissue = false;
    Publisher publisher;
    String name;
    String ISSN;
    List<Article> articleList;

    public Journal(String name, Publisher publisher, String ISSN) {
        this.name = name;
        this.publisher = publisher;
        this.ISSN = ISSN;
        this.articleList = new ArrayList<>();
    }

    public String toString(){
        return "name: " + name + ", Publisher name: " + publisher.name + ", Publisher location: " + publisher.location
                + ", ISSN: " + ISSN + ", Journal Article List: " + articleList + "\n";
    }
}
