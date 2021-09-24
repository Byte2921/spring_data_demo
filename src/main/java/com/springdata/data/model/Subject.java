package com.springdata.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    @Builder.Default
    @Column(columnDefinition = "boolean default false")
    private Boolean active = false;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "subject_students",
            joinColumns = @JoinColumn(name = "subjects_id"),
            inverseJoinColumns = @JoinColumn(name = "students_id")
    )
    private Set<Student> students = new HashSet<>();
}
