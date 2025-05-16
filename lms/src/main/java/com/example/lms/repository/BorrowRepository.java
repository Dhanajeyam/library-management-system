package com.example.lms.repository;

import com.example.lms.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BorrowRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT b.id,b.borrowed_date, b.due_date, " +
                "m.id AS member_id, m.name As member_name, m.phone, " +
                "bk.id AS book_id, bk.title, bk.author, bk.isbn " +
                "FROM borrow b " +
                "JOIN member m ON b.member_id = m.id " +
                "JOIN book bk On b.book_id = bk.id";
        return jdbcTemplate.queryForList(sql);
    }

    public int save(Borrow borrow) {
        return jdbcTemplate.update("INSERT INTO borrow (member_id, book_id, borrowed_date, due_date) VALUES (?, ?, ?, ?)",
        borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowedDate(), borrow.getDueDate());
    }

    public int update(Borrow borrow){
        return jdbcTemplate.update("UPDATE borrow SET member_id = ?, book_id = ?, borrowed_date = ?, due_date = ? WHERE id = ?",
                borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowedDate(), borrow.getDueDate(), borrow.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("Delete FROM borrow WHERE id = ?", id);
    }

    public int decrementBookStock(int bookId){
        return jdbcTemplate.update("UPDATE book Set available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0", bookId);
    }

    public int incrementBookStock(int bookId) {
        return jdbcTemplate.update("UPDATE book SET available_copies = available_copies + 1 WHERE id = ?", bookId);
    }

}
