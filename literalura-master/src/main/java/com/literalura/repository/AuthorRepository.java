package com.literalura.repository;

import com.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByDeathDateIsNull();
    List<Author> findByBirthDateBeforeAndDeathDateAfter(Date start, Date end);
}
