package com.example.recallbackend.pojo.domain;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.13
 */
@Data
public class UserRelation {
    private Integer id;
    private Integer parentId;
    private Integer childId;
}
