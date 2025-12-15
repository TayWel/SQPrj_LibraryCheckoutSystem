/*
*Taylor Welton
*MCS 4513
*Module 8 Project – Library Checkout System
*WHAT THIS CODE DOES
*“I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else’s work as my own.”
NEEDS COMMENTS!!
*/
package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {

    //Stores all books in the library
    private final List<Book> books = new ArrayList<>();

    //Stores all registered library patrons
    private final List<Patron> patrons = new ArrayList<>();

    //Stores all loans (active and returned)
    private final List<Loan> loans = new ArrayList<>();

    //Auto-incremented IDs
    private int nextBookId = 1; //Book ID #
    private int nextPatronId = 1; //Patron ID #
    private int nextLoanId = 1; //Loan ID #
    
    
    /*****************************/
    /***Week 1: Books & Patrons***/
    /*****************************/

    //Adds a new book to the library system. ISBN must be unique or argument is thrown.
    public Book addBook(String title, String author, String isbn, int copies) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn.trim())) {
                throw new IllegalArgumentException("Duplicate ISBN not allowed");
            }
        }

        Book book = new Book(nextBookId++, title, author, isbn, copies);
        books.add(book);
        return book;
    }

    //Returns an unmodifiable list of all books in the library system.
    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    //Finds a book by the ID# or returns null if book is not found.
    public Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    //Adds a new patron to the library system. ID# is created in system w/ auto incrementing.
    public Patron addPatron(String name, String email) {
        Patron patron = new Patron(nextPatronId++, name, email);
        patrons.add(patron);
        return patron;
    }

    //Returns an unmodifiable list of all library patrons.
    public List<Patron> getAllPatrons() {
        return Collections.unmodifiableList(patrons);
    }

    //Finds a patron by ID# or returns null if ID# is not found.
    public Patron findPatronById(int id) {
        for (Patron p : patrons) {
            if (p.getId() == id) return p;
        }
        return null;
    }

        
    /*******************************/
    /***Week 2: Checkout & Return***/
    /*******************************/
    
    
    

    /**
     * Checks out a book to a patron.
     *
     * @return the created Loan object
     * @throws IllegalArgumentException if IDs are invalid
     * @throws IllegalStateException if no copies are available
     */
    
    //System for checking a book out for a patron.
    public Loan checkoutBook(int bookId, int patronId) {

        //Validate book and patron
        Book book = findBookById(bookId); //Confirm book is available
        Patron patron = findPatronById(patronId); //Confirm patron ID exists

        //Book is not available
        if (book == null) {
            throw new IllegalArgumentException("Book ID not found");
        }
        //No matching patron ID was found
        if (patron == null) {
            throw new IllegalArgumentException("Patron ID not found");
        }

        //Confirm copies available are not 0.
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available for checkout");
        }

        //If copies are available, reduce copy count
        book.checkoutOneCopy();

        //Create a 14 day loan period from the current date.
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(14);

        //Create loan ID
        Loan loan = new Loan(nextLoanId++, bookId, patronId, today, dueDate);
        loans.add(loan);

        return loan;
    }

    /**
     * Returns a book for a given loan ID.
     *
     * @throws IllegalArgumentException if loan not found
     * @throws IllegalStateException if already returned
     */
    public void returnBook(int loanId) {

        //Locate loan ID
        Loan loan = findLoanById(loanId);
        if (loan == null) { //Loan not found
            throw new IllegalArgumentException("Loan ID not found");
        }

        if (loan.isReturned()) { //Loan is already returned.
            throw new IllegalStateException("Loan already returned");
        }

        //Increase copies of book/Restore book availability
        Book book = findBookById(loan.getBookId());
        if (book != null) {
            book.returnOneCopy();
        }

        //Mark loan as returned on the date.
        loan.markReturned(LocalDate.now());
    }


    //Finds a loan by ID#
    private Loan findLoanById(int id) {
        for (Loan l : loans) {
            if (l.getId() == id) return l;
        }
        return null;
    }

    //Returns an unmodifiable list of all created loans
    public List<Loan> getAllLoans() {
        return Collections.unmodifiableList(loans);
    }


    /*******************************/
    /***Week 3: Overdue Reporting***/
    /*******************************/

    /**
    * Returns a list of loans that are overdue as of "today".
    *
    * Overdue means:
    *  - the loan has NOT been returned
    *  - dueDate is BEFORE today (strictly earlier)
    *
    * Why take LocalDate today as a parameter?
    *  - It makes this method easy to unit test (tests can choose the date).
    */
    public List<Loan> getOverdueLoans(LocalDate today) {
        if (today == null) {
            throw new IllegalArgumentException("today cannot be null");
        }

        List<Loan> overdue = new ArrayList<>();

        for (Loan loan : loans) {
            boolean notReturned = !loan.isReturned();
            boolean pastDue = loan.getDueDate().isBefore(today);

            if (notReturned && pastDue) {
                overdue.add(loan);
            }
        }

        return overdue;
    }   

/**
 * Convenience method that uses the real current date.
 * This is useful for a demo app, but tests should call getOverdueLoans(today).
 */
public List<Loan> getOverdueLoans() {
    return getOverdueLoans(LocalDate.now());
}

/**
 * Returns a list of active (not returned) loans.
 * Useful for printing current checkouts in a demo.
 */
public List<Loan> getActiveLoans() {
    List<Loan> active = new ArrayList<>();
    for (Loan loan : loans) {
        if (!loan.isReturned()) {
            active.add(loan);
        }
    }
    return active;
}

    
}
