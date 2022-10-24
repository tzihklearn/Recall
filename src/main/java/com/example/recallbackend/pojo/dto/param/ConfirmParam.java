package com.example.recallbackend.pojo.dto.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.10.05
 */
@Data
public class ConfirmParam {

    @NotNull
    @JsonProperty("userId")
    Integer userId;

    @NotNull
    @JsonProperty("videoIds")
    List<Integer> videoIds;

}
