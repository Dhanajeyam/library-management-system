package com.example.lms.controller;

import com.example.lms.model.Borrow;
import com.example.lms.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowRepository borrowRepository;

    @GetMapping
    public List<Map<String, Object>> getAllBorrows() {
        return borrowRepository.findAll();
    }

    @PostMapping("/lend")
    public ResponseEntity<String> lendBook(@RequestBody Borrow borrow){
        int updated = borrowRepository.decrementBookStock(borrow.getBookId());
        if(updated == 0) {
            return ResponseEntity.badRequest().body("Book is not available for lending");
        }
        borrowRepository.save(borrow);
        return ResponseEntity.ok("Book lent successfully");
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable int id, @RequestBody Borrow borrow){
        borrow.setId(id);
        borrowRepository.update(borrow);
        borrowRepository.incrementBookStock(borrow.getBookId());
        return ResponseEntity.ok("Book returned successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrow(@PathVariable int id){
        borrowRepository.delete(id);
        return ResponseEntity.ok("Borrow record deleted");
    }




}
