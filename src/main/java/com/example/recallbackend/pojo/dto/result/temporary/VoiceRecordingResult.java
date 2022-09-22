package com.example.recallbackend.pojo.dto.result.temporary;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Data
@AllArgsConstructor
public class VoiceRecordingResult {

    String data;

    String videoUrl;

    String times;

    Integer state;

}
