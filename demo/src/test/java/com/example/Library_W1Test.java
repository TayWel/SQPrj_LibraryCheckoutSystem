package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class Library_W1Test {

    @Test
    void canAddAndListBooks() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719", 3);

        assertEquals(1, lib.getAllBooks().size());
        assertEquals("Dune", lib.getAllBooks().get(0).getTitle());
        assertEquals(3, lib.getAllBooks().get(0).getAvailableCopies());
    }

    @Test
    void duplicateIsbnRejected() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719", 3);

        assertThrows(IllegalArgumentException.class, () ->
            lib.addBook("Dune (2nd Copy)", "Frank Herbert", "9780441172719", 2)
        );
    }

    @Test
    void canAddPatron() {
        Library lib = new Library();
        Patron p = lib.addPatron("Taylor Welton", "taylor@example.com");

        assertNotNull(p);
        assertEquals(1, lib.getAllPatrons().size());
        assertEquals("Taylor Welton", lib.getAllPatrons().get(0).getName());
    }

}
