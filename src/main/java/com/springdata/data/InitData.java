package com.springdata.data;

import com.springdata.data.model.Laptop;
import com.springdata.data.model.Student;
import com.springdata.data.model.Subject;
import com.springdata.data.repository.LaptopRepository;
import com.springdata.data.repository.StudentRepository;
import com.springdata.data.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Order(2)
@Component
public class InitData implements CommandLineRunner {

    static final Logger LOGGER = LoggerFactory.getLogger(InitData.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    LaptopRepository laptopRepository;

    @Override
    public void run(String... args) throws Exception {
        Laptop laptop1 = Laptop.builder()
                .name("HP")
                .build();
        Laptop laptop2 = Laptop.builder()
                .name("Lenovo")
                .build();
        Student student1 = Student.builder()
                .firstName("Pali")
                .lastName("Nap")
                .email("pali_nap@test.com")
                .laptop(laptop1)
                .build();
        Student student2 = Student.builder()
                .firstName("Ödön")
                .lastName("Tök")
                .email("odon_tok@test.com")
                .laptop(laptop1)
                .build();
        Subject subject1 = Subject.builder()
                .subjectName("Matematika")
                .build();
        Subject subject2 = Subject.builder()
                .subjectName("Irodalom")
                .build();
        student1.setSubjects(new HashSet<>(
                Arrays.asList(subject1, subject2)
        ));
        student2.setSubjects(new HashSet<>(
                List.of(subject1)
        ));
        subject1.setStudents(new HashSet<>(
                Arrays.asList(student1, student2)
        ));
        subject2.setStudents(new HashSet<>(
                List.of(student2)
        ));
        laptop1.setStudents(new HashSet<>(
                Arrays.asList(student1, student2)
        ));
        laptopRepository.saveAll(new HashSet<>(
                Arrays.asList(laptop1, laptop2)
        ));
        LOGGER.info("Laptops saved");
        studentRepository.saveAll(new HashSet<>(
                Arrays.asList(student1, student2)
        ));
        LOGGER.info("Students saved!");
        subjectRepository.saveAll(new HashSet<>(
                Arrays.asList(subject1, subject2)
        ));
        LOGGER.info("Subjects saved!");
    }
}
