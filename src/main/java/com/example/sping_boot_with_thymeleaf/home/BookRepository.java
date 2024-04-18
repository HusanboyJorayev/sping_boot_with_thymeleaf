package com.example.sping_boot_with_thymeleaf.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("""
            select b from Book as b
            """)
    List<Book> findAllBooks();
}
