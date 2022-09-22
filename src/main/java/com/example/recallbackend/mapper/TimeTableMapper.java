package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.po.IndexGetAllPo;
import com.example.recallbackend.pojo.po.MemorandumPo;
import com.example.recallbackend.pojo.po.VoiceRecordingPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimeTableMapper {

//    List<TimeSchedulePo> selectScheduleByParentId(Integer parentId, Long startTime, Long endTime);

    List<IndexGetAllPo> selectInboxAllByParentId(@Param("parentId") Integer parentId, @Param("startTime") Long startTime,
                                                 @Param("endTime") Long endTime);

    List<VoiceRecordingPo> selectVoiceByUserId(@Param("scheduleBoxId") Integer scheduleBoxId,
                                               @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<MemorandumPo> selectMemorandumByUserId(Integer userId, Long time);

}
