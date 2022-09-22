package com.example.recallbackend.pojo.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Data
@AllArgsConstructor
public class MemorandumResult {

    String data;

    String videoUrl;

    String time;

    String length;
}
