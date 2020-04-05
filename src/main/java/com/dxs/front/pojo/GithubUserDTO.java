package com.dxs.front.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubUserDTO {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
