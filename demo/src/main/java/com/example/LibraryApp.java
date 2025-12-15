/*
*Taylor Welton
*MCS 4513
*Module 8 Project – Library Checkout System
*The main code of the Library Checkout System. It's what class the other classes and functions. Through this script a User can run a mock version of a library checkout system. With given inputs it can recall if a book is available, a patron's ID# and if they have any books checked out, when the book is due back, and if they have existing fines.
*“I have neither given nor received unauthorized aid in completing this work, nor have I presented someone else’s work as my own.”
NEEDS COMMENTS!!
*/
package com.example;

import java.time.LocalDate;
import java.awt.print.*;

public class LibraryApp {
    public static void main(String[] args) {
        System.out.println("Hello Library!");

        //Call the library class (core logic of the program is in the Library.java script)
        Library lib = new Library();

        //Add books
        Book twilgiht = lib.addBook("Twilight", "Stephenie Meyer", "9780316015844", 1);
        Book hungerGame = lib.addBook("The Hunger Games", "Suzanne Collins", "9780439023481", 2);
        Book dune = lib.addBook("Dune", "Frank Herbert", "9780441172719", 3);
        Book nineteen = lib.addBook("1984", "George Orwell", "9780451524935", 4);

        //Add library patron
        Patron patron = lib.addPatron("John Doe", "jDoe@mail.com");


        //Show books before checkout(title, ID, # of copies)
        System.out.println("=== Books BEFORE checkout ===");
        for (Book b : lib.getAllBooks()) {
            System.out.println(b.getId() + ": " + b.getTitle() + " | Available: " +
                               b.getAvailableCopies() + "/" + b.getTotalCopies());
        }

        //Checkout system
        Loan loan1 = lib.checkoutBook(dune.getId(), patron.getId());
        System.out.println("\nChecked out Dune. Loan ID = " + loan1.getId() +
                           " | Due = " + loan1.getDueDate());

        //Return system
        lib.returnBook(loan1.getId());
        System.out.println("Returned loan " + loan1.getId());

        //Create another checkout to demonstrate overdue reporting
        Loan loan2 = lib.checkoutBook(nineteen.getId(), patron.getId());
        System.out.println("\nChecked out 1984. Loan ID = " + loan2.getId() +
                           " | Due = " + loan2.getDueDate());

        //Scenario: Using a pre-programmed date, show a book is overdue
        LocalDate fakeToday = loan2.getDueDate().plusDays(1);

        System.out.println("\n=== Overdue Loans as of " + fakeToday + " ===");
        for (Loan overdue : lib.getOverdueLoans(fakeToday)) {
            System.out.println("Loan " + overdue.getId() + " is overdue (due " + overdue.getDueDate() + ")");
        }

        //Show books after checkout(title, ID, # of copies)
        System.out.println("\n=== Books AFTER operations ===");
        for (Book b : lib.getAllBooks()) {
            System.out.println(b.getId() + ": " + b.getTitle() + " | Available: " +
                               b.getAvailableCopies() + "/" + b.getTotalCopies());
        }

    }
}