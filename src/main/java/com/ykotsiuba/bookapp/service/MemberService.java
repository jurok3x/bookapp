package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.dto.MemberSaveRequestDTO;

import java.util.List;

public interface MemberService {
    MemberDTO saveMember(MemberSaveRequestDTO requestDTO);
    MemberDTO getMemberById(Long id);
    List<MemberDTO> getAllMembers();
    void deleteMember(Long id);
    MemberDTO updateMember(MemberSaveRequestDTO requestDTO, Long id);
}
