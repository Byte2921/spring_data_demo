package com.springdata.data.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subjectName;
    private Boolean active;
}
