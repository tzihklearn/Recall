package com.example.recallbackend.mapper;

import com.example.recallbackend.Service.BindingService;
import com.example.recallbackend.pojo.dto.param.NewlyBuildParam;
import com.example.recallbackend.pojo.dto.param.temporary.VideoScheduleParam;
import com.example.recallbackend.pojo.po.IndexGetAllPo;
import com.example.recallbackend.pojo.po.MemorandumPo;
import com.example.recallbackend.pojo.po.VoiceRecordingPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimeTableMapper {

    int insertBySchedule(@Param("parentId") Integer parentId, @Param("childId") Integer childId,
                         @Param("ScheduleBoxId") Integer ScheduleBoxId,
                         @Param("videoScheduleParamList") List<VideoScheduleParam> videoScheduleParamList);

    int insertMemorandum(@Param("newlyBuildParam") NewlyBuildParam newlyBuildParam, @Param("videoUrl") String videoUrl);

//    List<TimeSchedulePo> selectScheduleByParentId(Integer parentId, Long startTime, Long endTime);

    List<IndexGetAllPo> selectInboxAllByParentId(@Param("parentId") Integer parentId, @Param("startTime") Long startTime,
                                                 @Param("endTime") Long endTime);

    List<VoiceRecordingPo> selectVoiceByUserId(@Param("scheduleBoxId") Integer scheduleBoxId,
                                               @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<MemorandumPo> selectMemorandumByUserId(Integer userId, Long time);




}
