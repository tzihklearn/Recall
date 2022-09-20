package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tzih
 * @date 2022.09.20
 */
@Data
public class ChildNameParam {

    @NotNull
    private Integer parentId;

    @NotNull
    private Integer childId;

    @Pattern(regexp = "", message = "姓名不符合规范")
    @NotNull
    private String name;

}
