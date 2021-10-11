package com.springdata.data.service;

import com.springdata.data.exceptions.LaptopException;
import com.springdata.data.exceptions.StudentException;
import com.springdata.data.exceptions.SubjectException;
import com.springdata.data.model.Laptop;
import com.springdata.data.model.Student;
import com.springdata.data.model.Subject;
import com.springdata.data.repository.LaptopRepository;
import com.springdata.data.repository.StudentRepository;
import com.springdata.data.repository.SubjectRepository;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Transactional
@Service
@NoArgsConstructor
public class DemoService {

    static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    LaptopRepository laptopRepository;
    @PersistenceContext
    EntityManager entityManager;

    public Student saveStudent(Student student) {
        LOGGER.info("Saved" + student);
        return studentRepository.save(student);
    }

    public Subject saveSubject(Subject subject) {
        LOGGER.info("Saved " + subject);
        return subjectRepository.save(subject);
    }

    public Laptop saveLaptop(Laptop laptop) {
        LOGGER.info("Saved " + laptop);
        return laptopRepository.save(laptop);
    }

    public Student assignNewSubjectToStudent(Long studentId, Subject subject) {
        try {
            Optional<Student> response = studentRepository.findById(studentId);
            Student student = response.orElseThrow(() -> new StudentException(List.of(studentId)));
            Session session = entityManager.unwrap(Session.class);
            session.close();
            student.getSubjects().add(subject);
            subject.setStudents(new HashSet<>
                    (List.of(student)));
            return saveStudent(student);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new Student();
        }
    }

    public int activateSubjects(Iterable<Long> subjectIds) {
        LOGGER.info("Updated " + subjectIds);
        return subjectRepository.setSubjectActive(subjectIds);
    }

    public Set<Student> getSubjectAttenders(Long subjectId) {
        try {
            Optional<Subject> response = subjectRepository.findById(subjectId);
            Subject subject = response.orElseThrow(() -> new SubjectException(List.of(subjectId)));
            Session session = entityManager.unwrap(Session.class);
            Hibernate.initialize(subject.getStudents());
            session.close();
            LOGGER.info("Found students: " + subject.getStudents());
            return subject.getStudents();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new HashSet<>();
        }
    }

    public Set<Subject> getSubjectsBasedOnActivity(Boolean active) {
        try {
            Set<Subject> subjects = subjectRepository.findByActiveIs(active);
            LOGGER.info("Found subjects: " + subjects);
            return subjects;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new HashSet<>();
        }
    }

    public int deleteInactiveSubjects() {
        LOGGER.info("Subjects deleted");
        return subjectRepository.deleteInactiveSubjects();
    }

    public int studentCountOnLaptop(Long laptopId) {
        try {
            Optional<Laptop> response = laptopRepository.findById(laptopId);
            Laptop laptop = response.orElseThrow(() -> new LaptopException(List.of(laptopId)));
            Session session = entityManager.unwrap(Session.class);
            Hibernate.initialize(laptop.getStudents());
            session.close();
            LOGGER.info("Found students " + laptop.getStudents().size() + ": " + laptop.getStudents());
            return laptop.getStudents().size();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return -1;
        }
    }

    public Subject toggleSubjectActivity(Long subjectId) {
        try {
            Optional<Subject> response = subjectRepository.findById(subjectId);
            Subject subject = response.orElseThrow(() -> new SubjectException(List.of(subjectId)));
            Session session = entityManager.unwrap(Session.class);
            session.close();
            subject.setActive(!subject.getActive());
            LOGGER.info("Toggled subject activity: " + subject);
            subjectRepository.save(subject);
            return subject;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new Subject();
        }
    }
}
