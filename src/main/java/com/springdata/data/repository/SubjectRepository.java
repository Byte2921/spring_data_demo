package com.springdata.data.repository;

import com.springdata.data.exceptions.SubjectException;
import com.springdata.data.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Modifying
    @Query("UPDATE Subject s SET s.active = true WHERE s.id IN (:ids)")
    int setSubjectActive(@Param("ids") Iterable<Long> subjectIds);
    Set<Subject> findByActiveIs(Boolean active);
    @Modifying
    @Query("DELETE FROM Subject s WHERE s.active = false")
    int deleteInactiveSubjects();
}
