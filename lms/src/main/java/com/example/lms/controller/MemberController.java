package com.example.lms.controller;

import com.example.lms.model.Member;
import com.example.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        return ResponseEntity.ok(memberRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        memberRepository.save(member);
        return ResponseEntity.ok("Member added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable int id, @RequestBody Member member){
        member.setId(id);
        memberRepository.update(member);
        return ResponseEntity.ok("Member updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable int id) {
        memberRepository.delete(id);
        return ResponseEntity.ok("Member deleted successfully");
    }
}
