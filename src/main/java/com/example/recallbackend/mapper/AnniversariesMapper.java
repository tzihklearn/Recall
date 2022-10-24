package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.dto.param.AnniversaryParam;
import com.example.recallbackend.pojo.po.AnniversaryPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnniversariesMapper {

    int insertAnniversary(AnniversaryParam anniversaryParam);

    int deleteAnniversary(Integer id, Integer userId);

    int deleteByMore(Integer userId);

    List<AnniversaryPo> selectAnniversaryPoByUserId(@Param("userId") Integer userId, @Param("time") Long time);

}
