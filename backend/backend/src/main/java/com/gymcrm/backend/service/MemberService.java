package com.gymcrm.backend.service;


import com.gymcrm.backend.dto.MemberRequestDto;
import com.gymcrm.backend.dto.MemberRespose;

import java.util.List;

public interface MemberService {

    String registerMember(MemberRequestDto dto);

    String verifyOtp(String email, String otp);

    MemberRespose getMemberByEmail(String email);

    MemberRespose updateMeber(Long id, MemberRequestDto dto);

    void deleteMember(Long id);

    List<MemberRespose> getAllMembers();

    MemberRespose getMemberById(Long id);

}
