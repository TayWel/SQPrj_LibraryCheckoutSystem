package com.example;

import java.time.LocalDate;

public class Loan {

    private final int id;
    private final int bookId;
    private final int patronId;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnedDate; // null until returned

    public Loan(int id, int bookId, int patronId, LocalDate checkoutDate, LocalDate dueDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public int getPatronId() { return patronId; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }

    public boolean isReturned() { return returnedDate != null; }
    public LocalDate getReturnedDate() { return returnedDate; }

    public void markReturned(LocalDate date) {
        if (returnedDate != null) throw new IllegalStateException("Loan already returned");
        returnedDate = date;
    }

}
