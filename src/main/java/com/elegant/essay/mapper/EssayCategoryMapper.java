package com.elegant.essay.mapper;

import com.elegant.essay.model.EssayCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EssayCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EssayCategory record);

    int insertSelective(EssayCategory record);

    EssayCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EssayCategory record);

    int updateByPrimaryKey(EssayCategory record);

    List<EssayCategory> findCategoriesByLevel(@Param("level") Integer level);
}