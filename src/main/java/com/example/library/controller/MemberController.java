package com.example.library.controller;

import com.example.library.dto.MemberRequestDto;
import com.example.library.dto.MemberResponseDto;
import com.example.library.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Members", description = "Endpoint for managing members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) { this.memberService = memberService; }

    @GetMapping
    @Operation(summary = "Get all members", description = "Returns a list of all members from the database")
    public List<MemberResponseDto> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Returns a single member or 404 if not found")
    public ResponseEntity<MemberResponseDto> getMemberById(
            @Parameter(description = "ID of the member to retrieve")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    @Operation(summary = "Create new member", description = "Adds a new member to database")
    public ResponseEntity<MemberResponseDto> createMember(
            @Valid @RequestBody MemberRequestDto member
    ) {
        return ResponseEntity.status(201).body(memberService.createMember(member));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a member", description = "Updates an existing member's details or 404 if not found")
    public ResponseEntity<MemberResponseDto> updateMember(
            @Parameter(description = "ID of the member to update")
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto member
    ) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a member", description = "Removes a member from database or 404 if not found")
    public ResponseEntity<Void> deleteMember(
        @Parameter(description = "ID of the member to delete")
        @PathVariable Long id
    ) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

}
