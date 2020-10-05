package org.nypl.journalsystem;

public class Author {
    String id;
    String name;

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "id: " + id + " name: " + name;
    }
}
