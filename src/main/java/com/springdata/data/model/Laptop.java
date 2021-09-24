package com.springdata.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "laptop")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "laptop")
    private Set<Student> students = new HashSet<>();
}
