package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.result.GetParentResult;
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
                        @Param("parentName") String parentName);

    int deleteRelation(Integer parentId, Integer childId);

    RelationNamePo selectNameByUserId(Integer parentId, Integer childId);

    List<UserResult> selectChildByParentId(Integer parentId);

    List<GetParentResult> selectParentByChildId(Integer childId);

    List<UserResult> selectParentIdAndNameByChildId(Integer childId);

    Integer selectId(Integer childId, Integer parentId);

}
