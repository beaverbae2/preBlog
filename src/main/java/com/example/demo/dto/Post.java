package com.example.demo.dto;

import lombok.*;

import java.sql.Timestamp;


@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private String title;
    private String content;
    private Long uid;
    private int shows;
    private String datetime;
    private boolean status;

}
