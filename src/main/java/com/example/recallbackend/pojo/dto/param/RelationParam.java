package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tzih
 * @date 2022.09.23
 */
@Data
public class RelationParam {

    @NotNull
    Integer parentId;

    @NotNull
    Integer childId;

}
