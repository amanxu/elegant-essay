package com.elegant.essay.mapper;

import com.elegant.essay.model.dto.EssayQueryDto;
import com.elegant.essay.model.vo.EssayVo;

import java.util.List;

public interface EssaySelfMapper {

    List<EssayVo> findByFilter(EssayQueryDto queryDto);
}