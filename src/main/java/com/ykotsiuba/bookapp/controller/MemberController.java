package com.ykotsiuba.bookapp.controller;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.dto.MemberSaveRequestDTO;
import com.ykotsiuba.bookapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ykotsiuba.bookapp.entity.enums.MemberMessages.MEMBER_DELETED;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberSaveRequestDTO memberSaveRequestDTO) {
        MemberDTO createdMember = memberService.saveMember(memberSaveRequestDTO);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        MemberDTO member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody MemberSaveRequestDTO memberSaveRequestDTO) {
        MemberDTO updatedMember = memberService.updateMember(memberSaveRequestDTO, id);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(MEMBER_DELETED.getMessage());
    }

}
