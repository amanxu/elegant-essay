package com.elegant.essay.mapper;

import com.elegant.essay.model.ElegantEssay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElegantEssayMapper {
    int deleteByPrimaryKey(Long essayId);

    int insert(ElegantEssay record);

    int insertSelective(ElegantEssay record);

    ElegantEssay selectByPrimaryKey(Long essayId);

    int updateByPrimaryKeySelective(ElegantEssay record);

    int updateByPrimaryKey(ElegantEssay record);

    Long essayCount();

    int batchInsertEssay(@Param("list") List<ElegantEssay> elegantEssays);
}