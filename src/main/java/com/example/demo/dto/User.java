package com.example.demo.dto;

import lombok.*;

@Data
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String uid;
    private String password;
    private String nickname;
    private boolean status;


}
