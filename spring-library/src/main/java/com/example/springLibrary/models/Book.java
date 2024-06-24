package com.example.springLibrary.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Title shouldn't be empty")
    @Size(min=1, message = "Title should be more than 1 characters")
    private String title;
    @NotEmpty(message = "Author shouldn't be empty")
    @Size(min = 2, max = 200, message = "The name of author should be in between 2 to 200 characters")
    private String author;
    @Min(value = 0, message = "Year of publication should be greater than 0")
    private int yearOfPublication;

    public Book(){}

    public Book(String title, String author, int yearOfPublication) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
