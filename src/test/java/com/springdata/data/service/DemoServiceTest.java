package com.springdata.data.service;

import com.springdata.data.model.Laptop;
import com.springdata.data.model.Student;
import com.springdata.data.model.Subject;
import com.springdata.data.repository.LaptopRepository;
import com.springdata.data.repository.StudentRepository;
import com.springdata.data.repository.SubjectRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.configuration.IMockitoConfiguration;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DemoServiceTest {

    @Mock
    StudentRepository studentRepository;
    @Mock
    SubjectRepository subjectRepository;
    @Mock
    LaptopRepository laptopRepository;
    @Mock
    EntityManager entityManager;
    @Mock
    Session session;

    @InjectMocks
    DemoService demoService;

    private Student testStudent;
    private Subject testSubject;
    private Laptop testLaptop;

    @BeforeEach
    void initTestData() {
        MockitoAnnotations.openMocks(this);
        testLaptop = Laptop.builder()
                .name("HP")
                .build();

        testStudent = Student.builder()
                .firstName("Pali")
                .lastName("Nap")
                .email("pali_nap@test.com")
                .laptop(testLaptop)
                .build();

        testSubject = Subject.builder()
                .subjectName("Matematika")
                .build();

        testStudent.setSubjects(new HashSet<>(
                List.of(testSubject)));
        testSubject.setStudents(new HashSet<>(
                List.of(testStudent)
        ));
    }

    @Test
    void saveStudentShouldReturnProperStudent() {
        Mockito.when(studentRepository
                        .save(any(Student.class)))
                .thenReturn(testStudent);
        Student studentResponse = demoService.saveStudent(new Student());
        assertEquals(testStudent, studentResponse);
    }

    @Test
    void saveSubjectShouldReturnProperSubject() {
        Mockito.when(subjectRepository
                        .save(any(Subject.class)))
                .thenReturn(testSubject);
        Subject subjectResponse = demoService.saveSubject(new Subject());
        assertEquals(testSubject, subjectResponse);
    }

    @Test
    void saveLaptopShouldReturnProperLaptop() {
        Mockito.when(laptopRepository
                        .save(any(Laptop.class)))
                .thenReturn(testLaptop);
        Laptop laptopResponse = demoService.saveLaptop(new Laptop());
        assertEquals(testLaptop, laptopResponse);
    }

    @Test
    void savingNullThrowsException() {
        Mockito.when(studentRepository
                        .save(null))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> {
            demoService.saveStudent(null);
        });
    }

    @Test
    void assignNewSubjectToStudentShouldPutStudentInSubjectStudentsSet() {
        Subject subject = Subject.builder()
                .subjectName("Test")
                .build();
        Mockito.when(studentRepository
                        .findById(1L))
                .thenReturn(Optional.of(testStudent));
        Mockito.when(studentRepository
                        .save(any(Student.class)))
                .thenReturn(testStudent);
        Mockito.when(entityManager
                        .unwrap(Session.class))
                .thenReturn(session);
        demoService.assignNewSubjectToStudent(1L, subject);
        assertTrue(subject.getStudents().contains(testStudent));
    }

    @Test
    void activateSubjectsReturnProperNumberOfRows() {
        List<Long> ids = new ArrayList<>(
                Arrays.asList(1L, 2L));
        Mockito.when(subjectRepository
                        .setSubjectActive(ids))
                .thenReturn(2);
        assertEquals(2, demoService.activateSubjects(ids));
    }

    @Test
    void getSubjectAttendersReturnsTestStudent() {
        Set<Student> students = new HashSet<>(
                List.of(testStudent));
        Mockito.when(subjectRepository
                        .findById(1L))
                .thenReturn(Optional.of(testSubject));
        Mockito.when(entityManager
                        .unwrap(Session.class))
                .thenReturn(session);
        assertEquals(students, demoService.getSubjectAttenders(1L));
    }

    @Test
    void getSubjectsBasedOnActivityShouldReturnTestSubject() {
        Set<Subject> subjects = new HashSet<>(
                List.of(testSubject));
        Mockito.when(subjectRepository
                        .findByActiveIs(false))
                .thenReturn(subjects);
        assertTrue(demoService.getSubjectsBasedOnActivity(false).contains(testSubject));
    }

    @Test
    void deleteInactiveSubjectsProperNumberOfRows() {
        Mockito.when(subjectRepository
                        .deleteInactiveSubjects())
                .thenReturn(1);
        assertEquals(1, demoService.deleteInactiveSubjects());
    }

    @Test
    void studentCountOnLaptopReturnsProperNumber() {
        testLaptop.setStudents(new HashSet<>(
                List.of(testStudent)));
        Mockito.when(laptopRepository
                        .findById(1L))
                .thenReturn(Optional.of(testLaptop));
        Mockito.when(entityManager
                        .unwrap(Session.class))
                .thenReturn(session);
        assertEquals(1, demoService.studentCountOnLaptop(1L));
    }

    @Test
    void studentCountOnLaptopReturnsFailWithUnusedLaptop() {
        Mockito.when(laptopRepository
                        .findById(1L))
                .thenReturn(Optional.of(testLaptop));
        Mockito.when(entityManager
                        .unwrap(Session.class))
                .thenReturn(session);
        assertEquals(-1, demoService.studentCountOnLaptop(1L));
    }

    @Test
    void toggleSubjectActivityReturnsTrueWithTestSubject() {
        Mockito.when(subjectRepository
                        .findById(1L))
                .thenReturn(Optional.of(testSubject));
        Mockito.when(subjectRepository
                        .save(any(Subject.class)))
                .thenReturn(testSubject);
        Mockito.when(entityManager
                        .unwrap(Session.class))
                .thenReturn(session);
        assertTrue(demoService.toggleSubjectActivity(1L).getActive());
    }
}