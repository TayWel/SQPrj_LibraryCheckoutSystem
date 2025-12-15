package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class Library_W2Test {
    @Test
    void checkoutReducesAvailableCopies() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        lib.checkoutBook(book.getId(), patron.getId());

        assertEquals(0, book.getAvailableCopies());
    }

    @Test
    void returnRestoresAvailableCopies() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        Loan loan = lib.checkoutBook(book.getId(), patron.getId());
        lib.returnBook(loan.getId());

        assertEquals(1, book.getAvailableCopies());
    }

    @Test
    void cannotCheckoutWhenNoCopiesLeft() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        lib.checkoutBook(book.getId(), patron.getId());

        assertThrows(IllegalStateException.class, () ->
            lib.checkoutBook(book.getId(), patron.getId())
        );
    }

    @Test
    void invalidBookIdThrowsException() {
        Library lib = new Library();
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        assertThrows(IllegalArgumentException.class, () ->
            lib.checkoutBook(999, patron.getId())
        );
    }

}
