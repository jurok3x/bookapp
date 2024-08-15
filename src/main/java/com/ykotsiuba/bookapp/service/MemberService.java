package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.dto.MemberSaveRequestDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MemberService {
    /**
     * Saves a new member based on the provided request data.
     * If a member with the same name already exists, a new member is still created.
     *
     * @param requestDTO the data transfer object containing member details to be saved.
     * @return a {@link MemberDTO} representing the saved member.
     */
    MemberDTO saveMember(MemberSaveRequestDTO requestDTO);

    /**
     * Retrieves a member by its unique identifier.
     *
     * @param id the unique identifier of the member.
     * @return a {@link MemberDTO} representing the found member.
     * @throws EntityNotFoundException if no member is found with the specified id.
     */
    MemberDTO getMemberById(Long id);

    /**
     * Retrieves all members in the system.
     *
     * @return a {@link List} of {@link MemberDTO} objects representing all members.
     */
    List<MemberDTO> getAllMembers();

    /**
     * Deletes a member by its unique identifier.
     * If the member is associated with any books, it cannot be deleted.
     *
     * @param id the unique identifier of the member to be deleted.
     * @throws IllegalStateException if the member has associated books and cannot be deleted.
     */
    void deleteMember(Long id);

    /**
     * Updates the details of an existing member.
     * The member is identified by its unique identifier, and its name is updated
     * based on the provided request data.
     *
     * @param requestDTO the data transfer object containing updated member details.
     * @param id the unique identifier of the member to be updated.
     * @return a {@link MemberDTO} representing the updated member.
     */
    MemberDTO updateMember(MemberSaveRequestDTO requestDTO, Long id);
}
