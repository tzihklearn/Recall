package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.result.VideoListResult;
import com.example.recallbackend.pojo.dto.result.VideoPacketResult;
import com.example.recallbackend.pojo.dto.result.VoiceResult;
import com.example.recallbackend.pojo.po.VideoPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    int insertVideoByUserId(VideoPo videoPo);

    int insertVideoNoDataByUserId(VideoPo videoPo);

    List<VideoPacketResult> selectVideoByUserId(Integer userId);

    Integer selectIdByOrder(Integer userId);

    List<VoiceResult> selectVoiceByUserId(Integer userId);

    VoiceResult selectOneByUserId(Integer userId);

    List<VideoListResult> selectVideosByUserId(Integer userId);
}
