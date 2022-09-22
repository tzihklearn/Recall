package com.example.recallbackend.pojo.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Data
@AllArgsConstructor
public class InBoxGetAllResult {

    Integer userId;

    String name;

    Integer scheduleBoxId;

    String data;

    List<String> timeList;

}
