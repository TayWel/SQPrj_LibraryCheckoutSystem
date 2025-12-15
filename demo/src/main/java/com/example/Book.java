/*
*Taylor Welton
*MCS 4513
*Module 8 Project – Library Checkout System
*WHAT THIS CODE DOES
*“I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else’s work as my own.”
NEEDS COMMENTS!!
*/
//NEEDS COMMENTS!!

package com.example;

public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final String isbn;
    private final int totalCopies;
    private int availableCopies;

    public Book(int id, String title, String author, String isbn, int totalCopies) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Title required");
        if (author == null || author.trim().isEmpty()) throw new IllegalArgumentException("Author required");
        if (isbn == null || isbn.trim().isEmpty()) throw new IllegalArgumentException("ISBN required");
        if (totalCopies <= 0) throw new IllegalArgumentException("Copies must be > 0");

        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
        this.isbn = isbn.trim();
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    // Used later in Week 2/3, but safe to include now
    public void checkoutOneCopy() {
        if (availableCopies <= 0) throw new IllegalStateException("No copies available");
        availableCopies--;
    }

    public void returnOneCopy() {
        if (availableCopies >= totalCopies) throw new IllegalStateException("All copies already returned");
        availableCopies++;
    }

}
