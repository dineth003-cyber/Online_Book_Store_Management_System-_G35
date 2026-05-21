package org.example.customer_app.service;

import org.example.customer_app.model.*;
import java.util.*;

public class BookService {

    private List<Book> books = new ArrayList<>();

    public BookService() {
        Publisher pub = new Publisher("ABC Publications");

        // FIXED: EBook constructor එකට ගැලපෙන්න අන්තිමට file size (double) එකතු කළා
        books.add(new EBook("B001", "Java Basics", "IT", 1500, pub, 15.5));
        // BookService එකේ මේ ලයින් එක විතරක් මෙහෙම වෙනස් කරන්න:
        books.add(new PhysicalBook("B002", "Spring Boot Guide", "IT", 2500, pub, 1.5)); // අන්තිමට 1.5 එකතු කළා
        books.add(new EBook("B003", "Business Tips", "Business", 1200, pub, 5.0));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book findById(String id) {
        return books.stream()
                .filter(b -> b.getBookId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addBook(Book newBook) {
        books.add(newBook);
    }
}

