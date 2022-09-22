package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.result.temporary.FeedBackResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.OutBoxGetAllPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleBoxMapper {

    List<OutBoxGetAllPo> selectOutBoxByUserId(Integer childId, Long time);

    List<VoiceRecordingResult> selectVoiceById(Integer scheduleBoxId);

    List<FeedBackResult> selectFeedBackById(Integer scheduleBoxId);

}
