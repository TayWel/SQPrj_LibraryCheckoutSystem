package com.example;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class Library_W3Tests {

    @Test
    void overdueLoanIsIncludedInReport() {
        Library lib = new Library();

        // Add a book and patron so checkout is valid
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        // Checkout creates a loan due 14 days from "now"
        Loan loan = lib.checkoutBook(book.getId(), patron.getId());

        // Pretend today is far in the future so it MUST be overdue
        LocalDate fakeToday = loan.getDueDate().plusDays(1);

        List<Loan> overdue = lib.getOverdueLoans(fakeToday);

        assertEquals(1, overdue.size());
        assertEquals(loan.getId(), overdue.get(0).getId());
    }

    @Test
    void loanNotPastDueIsNotIncluded() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        Loan loan = lib.checkoutBook(book.getId(), patron.getId());

        // Pretend today is BEFORE the due date -> not overdue
        LocalDate fakeToday = loan.getDueDate(); // same day is NOT overdue (isBefore is false)

        List<Loan> overdue = lib.getOverdueLoans(fakeToday);

        assertEquals(0, overdue.size());
    }

    @Test
    void returnedLoanIsNotIncludedEvenIfPastDue() {
        Library lib = new Library();
        Book book = lib.addBook("Dune", "Frank Herbert", "9780441172719", 1);
        Patron patron = lib.addPatron("Taylor", "taylor@example.com");

        Loan loan = lib.checkoutBook(book.getId(), patron.getId());

        // Return the book (marks loan as returned)
        lib.returnBook(loan.getId());

        // Pretend we're past due, but returned loans should not count as overdue
        LocalDate fakeToday = loan.getDueDate().plusDays(10);

        List<Loan> overdue = lib.getOverdueLoans(fakeToday);

        assertEquals(0, overdue.size());
    }

    @Test
    void overdueTodayCannotBeNull() {
        Library lib = new Library();
        assertThrows(IllegalArgumentException.class, () -> lib.getOverdueLoans(null));
    }

}
