package com.example.demo.dto;

import lombok.*;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    private Long id;
    private String title;
    private String content;
    private Long uid;
    private int show;
    private String datetime;
    private boolean status;

}
