package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.result.UserResult;
import com.example.recallbackend.pojo.po.RelationNamePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRelationMapper {

    int insertRelationByUserId(Integer parentId, Integer childId);

    int updateChildName(@Param("parentId") Integer parentId, @Param("childId") Integer childId,
                        @Param("childName") String childName);

    int updateParentName(@Param("parentId") Integer parentId, @Param("childId") Integer childId,
                        @Param("childName") String ParentName);

    int deleteRelation(Integer parentId, Integer childId);

    RelationNamePo selectNameByUserId(Integer parentId, Integer childId);

    List<UserResult> selectChildByParentId(Integer parentId);

    List<UserResult> selectParentByChildId(Integer childId);

    List<UserResult> selectParentIdAndNameByChildId(Integer childId);



}
