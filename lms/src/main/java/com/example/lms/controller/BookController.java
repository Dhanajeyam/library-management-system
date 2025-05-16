package com.example.lms.controller;

// BookController.java

import com.example.lms.model.Book;
import com.example.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(bookRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok("Book added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody Book book) {
        book.setId(id);
        bookRepository.update(book);
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        bookRepository.delete(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookRepository.search(keyword);
    }
}
