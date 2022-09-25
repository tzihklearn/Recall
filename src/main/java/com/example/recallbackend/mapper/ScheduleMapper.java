package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.result.VideoPacketResult;
import com.example.recallbackend.pojo.po.VideoPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    int insertVideoByUserId(VideoPo videoPo);

    List<VideoPacketResult> selectVideoByUserId(Integer userId);



}
