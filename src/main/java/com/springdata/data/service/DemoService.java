package com.springdata.data.service;

import com.springdata.data.exceptions.StudentException;
import com.springdata.data.model.Student;
import com.springdata.data.model.Subject;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class DemoService {

    static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void assignNewSubjectToStudent(Long studentId, Subject subject) {
        try {
            Optional<Student> response = studentRepository.findById(studentId);
            Student student = response.orElseThrow(()-> new StudentException(List.of(studentId)));
            Session session = (Session) entityManager.unwrap(Session.class);
            session.close();
            student.getSubjects().add(subject);
            saveStudent(student);
            subject.setStudents(new ArrayList<>
                    (List.of(student)));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void saveStudent(Student student) {
        LOGGER.info("Saved" + studentRepository.save(student));
    }

    public void saveSubject(Subject subject) {
        LOGGER.info("Saved " + subjectRepository.save(subject));
    }
}
