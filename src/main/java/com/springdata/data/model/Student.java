package com.springdata.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects = new HashSet<>();
    @ManyToOne(cascade = CascadeType.MERGE)
    private Laptop laptop;
}
