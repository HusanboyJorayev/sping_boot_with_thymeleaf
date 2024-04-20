package com.example.sping_boot_with_thymeleaf.todoes;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
