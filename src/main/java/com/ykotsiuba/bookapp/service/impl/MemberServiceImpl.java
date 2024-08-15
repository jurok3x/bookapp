package com.ykotsiuba.bookapp.service.impl;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.dto.MemberSaveRequestDTO;
import com.ykotsiuba.bookapp.entity.Member;
import com.ykotsiuba.bookapp.exception.MemberCannotBeDeletedException;
import com.ykotsiuba.bookapp.mapper.MemberMapper;
import com.ykotsiuba.bookapp.repository.MemberRepository;
import com.ykotsiuba.bookapp.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ykotsiuba.bookapp.entity.enums.MemberMessages.MEMBER_CANNOT_BE_DELETED;
import static com.ykotsiuba.bookapp.entity.enums.MemberMessages.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    @Override
    public MemberDTO saveMember(MemberSaveRequestDTO requestDTO) {
        Member member = Member.builder()
                .name(requestDTO.getName())
                .build();
        Member savedMember = memberRepository.save(member);
        log.info("Saved member: {}", savedMember);
        return MemberMapper.toDTO(savedMember);
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Member member = findOrThrow(id);
        log.info("Found member with id: {}", id);
        return MemberMapper.toDTO(member);
    }

    private Member findOrThrow(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isEmpty()) {
            log.error("Member not found for id: {}", id);
        }
        return optionalMember.orElseThrow(() -> new EntityNotFoundException(String.format(MEMBER_NOT_FOUND.getMessage(), id)));
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteMember(Long id) {
        Member member = findOrThrow(id);
        if(!member.getBooks().isEmpty()) {
            throw new MemberCannotBeDeletedException(MEMBER_CANNOT_BE_DELETED.getMessage());
        }
        log.info("Deleted member with id: {}", id);
        memberRepository.delete(member);
    }

    @Override
    public MemberDTO updateMember(MemberSaveRequestDTO requestDTO, Long id) {
        Member member = findOrThrow(id);
        member.setName(requestDTO.getName());
        Member savedMember = memberRepository.save(member);
        log.info("Updating member: {}", savedMember);
        return MemberMapper.toDTO(savedMember);
    }
}
