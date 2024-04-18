package com.example.sping_boot_with_thymeleaf.home;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String name;
    private String author;
}
