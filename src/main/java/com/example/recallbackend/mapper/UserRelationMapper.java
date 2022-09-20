package com.example.recallbackend.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRelationMapper {

    int updateChildName(Integer parentId, Integer childId, String childName);

    int deleteRelation(Integer parentId, Integer child);

}
