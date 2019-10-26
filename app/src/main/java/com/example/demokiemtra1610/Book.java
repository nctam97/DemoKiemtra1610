package com.example.demokiemtra1610;

public class Book {


     int id_book;
     String title;
     int id_author;

    public Book() {

    }

    public Book(int id_book, String title, int id_author) {
        this.id_book = 0;
        this.title = "";
        this.id_author = 0;
    }

    public int getId() {
        return id_book;
    }

    public void setId(int id) {
        this.id_book = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthor() {
        return id_author;
    }

    public void setAuthor(int author) {
        this.id_author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id_book +
                ", title='" + title + '\'' +
                ", author='" + id_author + '\'' +
                '}';
    }


}
