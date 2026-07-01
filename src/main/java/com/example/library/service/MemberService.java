package com.example.library.service;

import com.example.library.dto.MemberRequestDto;
import com.example.library.dto.MemberResponseDto;
import com.example.library.entity.Member;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) { this.memberRepository = memberRepository; }

    public List<MemberResponseDto> getMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public MemberResponseDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return toResponseDto(member);
    }

    public MemberResponseDto createMember(MemberRequestDto newMember) {
        Member member = new Member(
                newMember.getFirstName(),
                newMember.getLastName(),
                newMember.getEmail(),
                newMember.getPhoneNumber()
        );

        return toResponseDto(memberRepository.save(member));
    }

    public MemberResponseDto updateMember(Long id, MemberRequestDto updateMember) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        existingMember.setFirstName(updateMember.getFirstName());
        existingMember.setLastName(updateMember.getLastName());
        existingMember.setEmail(updateMember.getEmail());
        existingMember.setPhoneNumber(updateMember.getPhoneNumber());

        return toResponseDto(memberRepository.save(existingMember));
    }

    public void deleteMember(Long id) {
        if(!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }


    private MemberResponseDto toResponseDto(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getMembershipDate(),
                member.isActive()
        );
    }

}
