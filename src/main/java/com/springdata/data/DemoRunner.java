package com.springdata.data;

import com.springdata.data.model.Subject;
import com.springdata.data.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    }

    @Override
    public void run(String... args) throws Exception {
        fiddleWithData();
    }
}
