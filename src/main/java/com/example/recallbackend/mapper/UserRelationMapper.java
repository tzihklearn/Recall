package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.po.RelationNamePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRelationMapper {

    int updateChildName(@Param("parentId") Integer parentId, @Param("childId") Integer childId,
                        @Param("childName") String childName);

    int deleteRelation(Integer parentId, Integer child);

    RelationNamePo selectNameByUserId(Integer parentId, Integer childId);

    List<String> selectChildNamesByParentId(Integer parentId);

}
