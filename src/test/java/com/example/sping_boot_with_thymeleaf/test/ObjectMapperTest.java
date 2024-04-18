package com.example.sping_boot_with_thymeleaf.test;

import com.example.sping_boot_with_thymeleaf.home.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ObjectMapperTest {

    @Test
    void sampleCode() throws Exception {
        String bookJson = """
                {
                "id":1,
                "name":"Husanboy",
                "author":"Jorayev"
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(bookJson, Book.class);
        System.out.println(book);
        String string = mapper.writeValueAsString(book);
        System.out.println(string);
    }

    @Test
    void objectFromFile() throws Exception {
        File file = new File("jsonBook");
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(file, Book.class);
        System.out.println(book);
        System.out.println(book.getId());
    }
}
