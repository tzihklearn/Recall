package com.example.recallbackend.pojo.dto.result;

import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Data
public class InboxDetailsResult {

    String name;

    List<VoiceRecordingResult> voiceRecordingList;

}
