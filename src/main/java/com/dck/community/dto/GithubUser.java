package com.dck.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String avatarUrl;
    private String bio;
}
