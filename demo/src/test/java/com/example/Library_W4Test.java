package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class Library_W4Test {

    @Test
    void returnBook_invalidLoanIdThrows() {
        Library lib = new Library();

        // Returning a loan that does not exist should throw an exception
        assertThrows(IllegalArgumentException.class, () -> lib.returnBook(999));
    }

    @Test
    void returnBook_twiceThrows() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        // Checkout creates a loan
        Loan loan = lib.checkoutBook(book.getId(), patron.getId());

        // First return succeeds
        lib.returnBook(loan.getId());
        assertEquals(1, book.getAvailableCopies());

        // Second return should fail because the loan is already returned
        assertThrows(IllegalStateException.class, () -> lib.returnBook(loan.getId()));
    }

    @Test
    void checkout_invalidPatronIdThrows() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);

        // Patron does not exist -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> lib.checkoutBook(book.getId(), 999));
    }

    @Test
    void checkout_invalidBookIdThrows() {
        Library lib = new Library();
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        // Book does not exist -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> lib.checkoutBook(999, patron.getId()));
    }

    @Test
    void checkoutThenReturn_restoresAvailability_andLoanMarkedReturned() {
        Library lib = new Library();
        Book book = lib.addBook("1984", "George Orwell", "9780451524935", 2);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        // Checkout decreases available copies by 1
        Loan loan = lib.checkoutBook(book.getId(), patron.getId());
        assertEquals(1, book.getAvailableCopies());

        // Return restores availability and marks the loan returned
        lib.returnBook(loan.getId());
        assertEquals(2, book.getAvailableCopies());
        assertTrue(loan.isReturned());
    }

}
