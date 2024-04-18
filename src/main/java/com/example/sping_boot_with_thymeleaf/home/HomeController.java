package com.example.sping_boot_with_thymeleaf.home;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    private final JdbcTemplate jdbcTemplate;
    private final BookRepository bookRepository;

    public HomeController(JdbcTemplate jdbcTemplate, BookRepository bookRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message");
        String sql = "select * from book";
        // List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
        List<Book> books = this.bookRepository.findAllBooks();
        model.addAttribute("books", books);
        return "home";
    }


    @PostMapping("/book/create")
    public String bookCreate(@ModelAttribute BookDto dto) {
        /*String sql = "insert into book(name,author) values(?,?);";
        jdbcTemplate.update(sql, dto.getName(), dto.getAuthor());*/
        Book book = Book.builder()
                .name(dto.getName())
                .author(dto.getAuthor())
                .build();
        this.bookRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/delete/book/{id}")
    public String delete(@PathVariable Integer id) {
        this.bookRepository.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/book/create")
    public String bookCreatePage() {
        return "book";
    }
}
