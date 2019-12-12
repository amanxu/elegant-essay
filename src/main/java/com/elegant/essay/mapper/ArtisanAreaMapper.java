package com.elegant.essay.mapper;

import com.elegant.essay.model.ArtisanArea;

public interface ArtisanAreaMapper {
    int deleteByPrimaryKey(Long areaId);

    int insert(ArtisanArea record);

    int insertSelective(ArtisanArea record);

    ArtisanArea selectByPrimaryKey(Long areaId);

    int updateByPrimaryKeySelective(ArtisanArea record);

    int updateByPrimaryKey(ArtisanArea record);
}