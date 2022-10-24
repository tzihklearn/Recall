package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.param.CreatOutBoxParam;
import com.example.recallbackend.pojo.dto.result.temporary.FeedBackResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.OutBoxGetAllPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleBoxMapper {

    int insertScheduleByUserId(@Param("parentId") Integer parentId, @Param("childId") Integer childId,
                               @Param("dayTime") Long time, @Param("relationId") Integer relationId);

    int updateUnFeedbackById(Integer Id);

    List<OutBoxGetAllPo> selectOutBoxByUserId(@Param("childId") Integer childId, @Param("keyWord") String keyWord,
                                              @Param("time") Long time);

    List<VoiceRecordingResult> selectVoiceById(Integer scheduleBoxId);

    List<FeedBackResult> selectFeedBackById(Integer scheduleBoxId);

//    Integer selectIdByUserIdAndTime(@Param("parentId") Integer parentId, @Param("childId") Integer childId,
//                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime);
    Integer selectIdByUserIdAndTime(@Param("parentId") Integer parentId, @Param("childId") Integer childId);


}
