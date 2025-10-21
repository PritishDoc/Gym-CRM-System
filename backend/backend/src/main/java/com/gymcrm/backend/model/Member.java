package com.gymcrm.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gymcrm.backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String contactNo;
    private String gender;
    private String membership;
    private String otp;
    private boolean verified;
    private String preferredTime;
    private Integer age;
//    private String trainer;

    // replaced String trainer with Trainer relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    @JsonBackReference
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String date_of_Joining;
}
