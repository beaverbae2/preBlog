package com.example.demo.dto;

import lombok.*;

@Data
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private Long id;
    private String content;
    private Long uid;
    private Long pid;
    private String datetime;
    private boolean status;


}
