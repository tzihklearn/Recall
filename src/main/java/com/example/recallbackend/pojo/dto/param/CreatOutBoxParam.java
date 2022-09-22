package com.example.recallbackend.pojo.dto.param;

import com.example.recallbackend.pojo.dto.param.temporary.VideoScheduleParam;
import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @date 2022.09.23
 */
@Data
public class CreatOutBoxParam {

    Integer userId;

    Integer parentId;

    Long boxTime;

    List<VideoScheduleParam> videoScheduleParamList;

}
