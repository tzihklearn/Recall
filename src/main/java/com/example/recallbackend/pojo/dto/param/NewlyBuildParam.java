package com.example.recallbackend.pojo.dto.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.26
 */
@Data
@AllArgsConstructor
public class NewlyBuildParam {

    Integer userId;

    String data;

    Long time;

}
