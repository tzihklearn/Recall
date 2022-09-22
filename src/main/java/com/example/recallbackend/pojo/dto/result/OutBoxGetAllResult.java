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
public class OutBoxGetAllResult {

    Integer parentId;

    String name;

    Integer scheduleBoxId;

    String data;

    List<String> timeList;

    Integer unFeedbackNum;

}
