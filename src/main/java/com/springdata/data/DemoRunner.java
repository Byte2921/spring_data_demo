package com.springdata.data;

import com.springdata.data.model.Student;
import com.springdata.data.model.Subject;
import com.springdata.data.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Order(3)
@Component
public class DemoRunner implements CommandLineRunner {

    @Autowired
    DemoService demoService;

    private void fiddleWithData() {
        Subject subject = Subject.builder()
                .subjectName("Nyelvtan")
                .build();
        demoService.assignNewSubjectToStudent(1L, subject);
        demoService.saveSubject(subject);
        demoService.activateSubjects(new ArrayList<>(Arrays.asList(1L, 2L)));
        Set<Student> students = demoService.getSubjectAttenders(1L);
        Set<Subject> subjects = demoService.getSubjectsBasedOnActivity(true);
        demoService.deleteInactiveSubjects();
        demoService.studentCountOnLaptop(2L);

    }

    @Override
    public void run(String... args) throws Exception {
        fiddleWithData();
    }
}
