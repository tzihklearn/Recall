package com.example.recallbackend.pojo.dto.param;

import com.example.recallbackend.pojo.dto.param.temporary.VideoScheduleParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.23
 */
@Data
public class CreatOutBoxParam {

    @NotNull
    Integer userId;

    @NotNull
    Integer parentId;

    @NotNull
    Long boxTime;

    @NotNull
    List<VideoScheduleParam> videoScheduleParamList;

}
