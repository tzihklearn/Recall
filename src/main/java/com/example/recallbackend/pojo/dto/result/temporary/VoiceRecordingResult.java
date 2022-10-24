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

    Integer voiceId;

    String data;

    String videoUrl;

    String times;

    Long timeStamp;

    Integer state;

}
