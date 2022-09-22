package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.result.VideoPacketResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    List<VideoPacketResult> selectVideoByUserId(Integer userId);

}
