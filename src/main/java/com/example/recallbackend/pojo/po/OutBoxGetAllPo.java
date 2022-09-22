package com.example.recallbackend.pojo.po;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Data
public class OutBoxGetAllPo {

    Integer userId;

    String name;

    Integer scheduleBoxId;

    String data;

    String times;

    Integer unFeedbackNum;
}
