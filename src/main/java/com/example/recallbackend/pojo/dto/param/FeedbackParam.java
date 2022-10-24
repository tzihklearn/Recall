package com.example.recallbackend.pojo.dto.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.28
 */
@Data
public class FeedbackParam {

    @JsonProperty("parentId")
    Integer userId;

    Integer childId;

    Integer scheduleBoxId;

    Integer length;

}
