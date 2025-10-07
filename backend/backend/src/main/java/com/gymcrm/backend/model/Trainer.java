package com.gymcrm.backend.model;

import com.gymcrm.backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String contactNo;
    private String gender;
    private Integer age;
    private String address;

    private Integer experienceYears;
    private String preferredTime;

    private String otp;
    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String dateOfJoining;

    // Relationships
    @OneToMany(mappedBy = "trainer")
    private List<Member> assignedMembers;   // One trainer to many members

}
