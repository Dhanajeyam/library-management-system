package com.example.lms.repository;

import com.example.lms.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    public int save(Book book) {
        return jdbcTemplate.update("INSERT INTO book (title, author, isbn, published_date, available_copies) VALUES (?, ?, ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies());
    }

    public int update(Book book) {
        return jdbcTemplate.update("UPDATE book SET title = ?, author = ?, isbn = ?, published_date = ?, available_copies = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies(), book.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public List<Book> search(String keyword) {
        String query = "SELECT * FROM book WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? OR LOWER(isbn) LIKE ?";
        String param = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Book.class), param, param, param);
    }
}