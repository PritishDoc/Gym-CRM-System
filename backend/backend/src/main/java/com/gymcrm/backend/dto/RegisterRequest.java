package com.gymcrm.backend.dto;

import com.gymcrm.backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullname;
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;
    private String password;
    private Role role; // Add this field

    // Either keep this if you want custom logic
//    public Role getRole() {
//        return this.role; // Return the role field
//    }

    // OR remove it completely and let Lombok @Data generate it
}