package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Data
public class MemorandumParam {

    Integer childUserId;

    Integer parentId;

    Long time;

}
